package com.amadeus.springblogdemo.service;

import com.amadeus.springblogdemo.domain.BlogInfo;
import com.amadeus.springblogdemo.pojo.request.AddBlogInfoRequest;
import com.amadeus.springblogdemo.pojo.request.UpBlogRequest;
import com.amadeus.springblogdemo.pojo.response.BlogInfoResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BlogInfoService extends IService<BlogInfo> {

    List<BlogInfoResponse> getList();

    BlogInfoResponse getBlogDetail(Integer blogId);

    Boolean addBlog(AddBlogInfoRequest blogInfo);

    Boolean deleteBlog(Integer blogId);

    Boolean updateBlog(UpBlogRequest blogRequest);
}
