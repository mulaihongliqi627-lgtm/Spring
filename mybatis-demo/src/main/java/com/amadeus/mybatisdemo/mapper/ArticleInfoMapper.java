package com.amadeus.mybatisdemo.mapper;


import com.amadeus.mybatisdemo.model.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleInfoMapper {

//    根据文章id查作者信息
    ArticleInfo queryArticleInfoById(Integer id);
}
