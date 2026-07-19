package com.amadeus.user.aspect;

import com.amadeus.user.annotation.LogOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
// 此切面依赖 Servlet 请求类型；WebFlux 网关不应注册它。
@ConditionalOnWebApplication(type = Type.SERVLET)
public class LogOperationAspect {

    private final  ObjectMapper objectMapper;

    public LogOperationAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }


    // 切点包名需与当前类导入的注解保持一致。
    @Around("@annotation(com.amadeus.user.annotation.LogOperation)")
    public Object logOPeration(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1.获取访问的接口和方法
        long startTime = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LogOperation logOperation = method.getAnnotation(LogOperation.class);

        String methodName = method.getName();
        String operation = logOperation.value().isEmpty() ? methodName : logOperation.value();

        // 2. 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 3. 记录请求信息
        if(logOperation.logRequest()){
            logRequest(operation, joinPoint, request);
        }

        // 4. 记录响应信息
        Object result = joinPoint.proceed();
        if(logOperation.logResponse()){
            logResponse(operation, result,startTime, logOperation.logExecutionTime());
        }
        return result;
    }


    /**
     * 记录响应信息
     * @param operation 操作描述
     * @param result 响应结果
     * @param startTime 开始时间
     * @param logTime 是否记录时间
     */
    private void logResponse(String operation, Object result, long startTime, boolean logTime) {
        //1. 记录响应信息
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("[").append(operation).append("] 响应参数");

        //2. 记录响应结果
        if(result != null){
            try {
                logBuilder.append("result = ").append(maskSensitiveData(objectMapper.writeValueAsString(result)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //记录操作耗时
        if(logTime){
            long endTime = System.currentTimeMillis();
            logBuilder.append(",耗时: ").append(endTime - startTime).append("ms");
        }
    }


    /**
     * 记录请求参数
     * @param operation
     * @param joinPoint
     * @param request
     */
    private void logRequest(String operation, ProceedingJoinPoint joinPoint, HttpServletRequest request){
        // 1.记录请求描述
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("[").append(operation).append("] 请求参数: ");


        // 2.记录请求路径和方法
        if(null != request){
            logBuilder.append("method = ").append(request.getMethod())
                    .append("url = ").append(request.getRequestURI());

            // 3.添加请求参数
            String queryString = request.getQueryString();
            if(null != queryString && !queryString.isEmpty()){
                logBuilder.append(", queryString = ").append(maskSensitiveData(queryString));//数据脱敏
            }

            //4.获取令牌
            String authorization = request.getHeader("Authorization");
            if(null != authorization && !authorization.isEmpty()){
                logBuilder.append(", authorization = ")
                        .append(maskToken(authorization));
            }
        }

        // 5.记录方法参数
        Object []args = joinPoint.getArgs();
        if(null != args || args.length > 0){
            logBuilder.append(", args = [");
            //遍历args数组,添加参数
            for(int i = 0;i < args.length;i++){
                Object arg = args[i];
                if(i != 0){
                    logBuilder.append( ",");
                }
                if(null == arg){
                    logBuilder.append("null");
                }else if(isFile(arg)){
                    logBuilder.append("name-").append(getFileName(arg));
                }else if(isHttpServletResponse(arg)){
                    logBuilder.append("下载内容无法打印");
                }else{
                    try{
                        String argStr = maskSensitiveData(objectMapper.writeValueAsString(arg));
                        logBuilder.append(argStr);
                    }catch (JsonProcessingException e){
                        logBuilder.append("获取参数异常");
                    }
                }
                logBuilder.append("]");
            }
            log.info(logBuilder.toString());
        }


    }

    /**
     * 根据反射靠对象来复原文件名
     * @param object 文件对象
     * @return 文件名
     */
    private String getFileName(Object object) {
        try {
            Method method =  object.getClass().getMethod("getOriginalFilename");
            return (String) method.invoke(object);
        } catch (Exception e) {
            return "文件未知!";
        }
    }

    /**
     * 根据反射来判断是否需要打印对象参数
     * @param object 入参
     * @return 是或者否
     */
    private boolean isHttpServletResponse(Object object) {
        return object != null && object.getClass().getName().contains("HttpServletResponse");
    }

    /**
     * 根据反射判断对象是否是文件
     * @param object
     * @return
     */
    private boolean isFile(Object object) {
        return object != null && object.getClass().getName().contains("MultipartFile");
    }

    /**
     * 对token进行脱敏
     * @param authorization 令牌
     * @return
     */
    private String maskToken(String authorization) {
        return "****";
    }


    /**
     *
     * @param data 原始数据
     * @return 脱敏数据
     */
    private String maskSensitiveData(String data) {
        // 脱敏密码字段
        data = data.replaceAll("\"password\"\\s*:\\s*\"[^\"]*\"", "\"password\":\"***\"");
        data = data.replaceAll("\"passwordHash\"\\s*:\\s*\"[^\"]*\"", "\"passwordHash\":\"***\"");

        // 脱敏token字段
        data = data.replaceAll("\"token\"\\s*:\\s*\"[^\"]*\"", "\"token\":\"***\"");
        data = data.replaceAll("\"authorization\"\\s*:\\s*\"[^\"]*\"", "\"authorization\":\"***\"");

        // 脱敏邮箱
        data = data.replaceAll("\"email\"\\s*:\\s*\"([^\"]*@[^\"]*)\"", "\"email\":\"***\"");

        return data;
    }

}
