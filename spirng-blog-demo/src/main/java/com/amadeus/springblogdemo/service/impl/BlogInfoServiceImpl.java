package com.amadeus.springblogdemo.service.impl;

import com.amadeus.springblogdemo.common.exception.BlogException;
import com.amadeus.springblogdemo.common.utils.BeanConvert;
import com.amadeus.springblogdemo.domain.BlogInfo;
import com.amadeus.springblogdemo.mapper.BlogInfoMapper;
import com.amadeus.springblogdemo.pojo.request.AddBlogInfoRequest;
import com.amadeus.springblogdemo.pojo.request.UpBlogRequest;
import com.amadeus.springblogdemo.pojo.response.BlogInfoResponse;
import com.amadeus.springblogdemo.service.BlogInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {

    @Override
    public List<BlogInfoResponse> getList() {
        List<BlogInfo> blogInfos = lambdaQuery()
                .eq(BlogInfo::getDeleteFlag, 0)
                .orderByDesc(BlogInfo::getId)
                .list();

        return blogInfos.stream().map(blogInfo -> {
            BlogInfoResponse response = new BlogInfoResponse();
            BeanUtils.copyProperties(blogInfo, response);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public BlogInfoResponse getBlogDetail(Integer blogId) {
        if (blogId == null) {
            throw new BlogException("博客ID不能为空");
        }
        BlogInfo blogInfo = getOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId, blogId)
                .eq(BlogInfo::getDeleteFlag, 0));
        if (blogInfo == null) {
            throw new BlogException("博客不存在");
        }
        return BeanConvert.convert(blogInfo);
    }

    @Override
    public Boolean addBlog(AddBlogInfoRequest request) {
        if (request == null) {
            throw new BlogException("博客内容不能为空");
        }
        if (request.getUserId() == null) {
            throw new BlogException("用户ID不能为空");
        }
        try {
            BlogInfo blogInfo = BeanConvert.convert(request);
            blogInfo.setDeleteFlag(0);
            blogInfo.setCreateTime(LocalDateTime.now());
            blogInfo.setUpdateTime(LocalDateTime.now());
            return save(blogInfo);
        } catch (Exception e) {
            log.warn("add blog failed", e);
            return false;
        }
    }

    @Override
    public Boolean deleteBlog(Integer blogId) {
        if (blogId == null) {
            throw new BlogException("博客ID不能为空");
        }
        BlogInfo blogInfo = getById(blogId);
        if (blogInfo == null || Integer.valueOf(1).equals(blogInfo.getDeleteFlag())) {
            throw new BlogException("博客不存在");
        }
        blogInfo.setDeleteFlag(1);
        blogInfo.setUpdateTime(LocalDateTime.now());
        return updateById(blogInfo);
    }

    @Override
    public Boolean updateBlog(UpBlogRequest request) {
        if (request == null || request.getId() == null) {
            throw new BlogException("博客ID不能为空");
        }
        BlogInfo blogInfo = getById(request.getId());
        if (blogInfo == null || Integer.valueOf(1).equals(blogInfo.getDeleteFlag())) {
            throw new BlogException("博客不存在");
        }
        BeanUtils.copyProperties(request, blogInfo);
        blogInfo.setUpdateTime(LocalDateTime.now());
        return updateById(blogInfo);
    }
}
