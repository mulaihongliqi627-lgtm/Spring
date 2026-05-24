package com.amadeus.springblogdemo.controller;


import com.amadeus.springblogdemo.domain.BlogInfo;
import com.amadeus.springblogdemo.pojo.request.AddBlogInfoRequest;
import com.amadeus.springblogdemo.pojo.request.UpBlogRequest;
import com.amadeus.springblogdemo.pojo.response.BlogInfoResponse;
import com.amadeus.springblogdemo.pojo.response.Result;
import com.amadeus.springblogdemo.service.BlogInfoService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource(name = "blogInfoServiceImpl")
    private BlogInfoService blogInfoService;

    @RequestMapping("/getList")
    public Result<List<BlogInfoResponse>> getList(){
        return Result.ok(blogInfoService.getList());
    }

    @RequestMapping("/getBlogDetail")
    public Result<BlogInfoResponse> getBlogDetail(@NotNull Integer blogId){
        log.info("getBlogById, blogId :{}",blogId);
        return Result.ok(blogInfoService.getBlogDetail(blogId));
    }


    @RequestMapping("/addBlog")
    public Boolean addBlog(AddBlogInfoRequest addBlogInfoRequest){
        log.info("发布博客,blogRequest:{}",addBlogInfoRequest);
        return blogInfoService.addBlog(addBlogInfoRequest);
    }


    @RequestMapping("/deleteBlog")
    public Boolean deleteBlog(@NotNull Integer blogId){
        log.info("删除博客,blogId:{}",blogId);
        return blogInfoService.deleteBlog(blogId);
    }

    @RequestMapping("/updateBlog")
    public Boolean updateBlog(UpBlogRequest upBlogRequest){
        log.info("更新博客,blogRequest:{}",upBlogRequest);
        return blogInfoService.updateBlog(upBlogRequest);
    }



}
