package com.amadeus.lotterysystem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class RedisUtil {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 设置redis中key对应的value
     * @param key
     * @param value
     */
    public boolean set(String key,String value){
        try{
            stringRedisTemplate.opsForValue().set(key,value);
            log.info("redis中设置值成功，key={},value={}",key,value);
        }catch (Exception e){
            log.error("redis中设置值失败，key={},value={}",key,value,e);
            return false;
        }
        return true;
    }


    /**
     * 设置redis中key对应的value，并设置过期时间 ,单位(秒)
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key,String value,long time){
        try{
            stringRedisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
            log.info("redis中设置值成功，key={},value={},time={}",key,value,time);
        }catch (Exception e){
            log.error("redis中设置值失败，key={},value={},time={}",key,value,time,e);
            return false;
        }
        return  true;
    }


    /**
     * 删除redis中key
     * @param key
     * @return
     */

    public boolean delKey(String key){
        try{
            boolean result = stringRedisTemplate.delete(key);
            log.info("redis中删除key成功，key={},result={}",key,result);
        }catch (Exception e){
            log.error("redis中删除key失败，key={}",key,e);
            return false;
        }
        return true;
    }


    /**
     * 获取redis中key对应的value
     * @param key
     * @return
     */

    public String get(String key){
        try{
            String value = stringRedisTemplate.opsForValue().get(key);
            log.info("redis中获取值成功，key={},value={}",key,value);
            return value;
        }catch (Exception e){
            log.error("redis中获取值失败，key={}",key,e);
            return "获取值失败";
        }
    }

    public boolean hasKey(String key){
        try{
            boolean result = stringRedisTemplate.hasKey(key);
            log.info("redis中判断key是否存在成功，key={},result={}",key,result);
        }catch (Exception e){
            log.error("redis中判断key是否存在失败，key={}",key,e);
            return false;
        }
        return true;
    }

}
