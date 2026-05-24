package com.amadeus.springblogdemo.common.utils;

import com.amadeus.springblogdemo.domain.BlogInfo;
import com.amadeus.springblogdemo.domain.UserInfo;
import com.amadeus.springblogdemo.pojo.request.AddBlogInfoRequest;
import com.amadeus.springblogdemo.pojo.response.BlogInfoResponse;
import com.amadeus.springblogdemo.pojo.response.UserInfoResponse;
import org.springframework.beans.BeanUtils;

public class BeanConvert {
    public static BlogInfo convert(AddBlogInfoRequest request) {
        if (request == null) {
            return null;
        }
        BlogInfo blogInfo = new BlogInfo();
        BeanUtils.copyProperties(request, blogInfo);
        return blogInfo;
    }

    public static BlogInfoResponse convert(BlogInfo blogInfo) {
        if(blogInfo == null){
            return null;
        }
        try{
            BlogInfoResponse blogInfoResponse = new BlogInfoResponse();
            BeanUtils.copyProperties(blogInfo, blogInfoResponse);
            return blogInfoResponse;
        }catch (Exception e){
            throw new RuntimeException("Bean对象转换失败");
        }
    }

    public static UserInfoResponse convert(UserInfo userInfo){
        if(userInfo == null){
            return null;
        }
        try{
            UserInfoResponse userInfoResponse = new UserInfoResponse();
            BeanUtils.copyProperties(userInfo, userInfoResponse);
            return userInfoResponse;
        }catch (Exception e){
            throw new RuntimeException("Bean对象转换失败");
        }
    }
}
