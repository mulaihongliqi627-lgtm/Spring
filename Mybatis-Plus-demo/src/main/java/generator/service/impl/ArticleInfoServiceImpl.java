package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.ArticleInfo;
import generator.service.ArticleInfoService;
import generator.mapper.ArticleInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Amadeus
* @description 针对表【article_info】的数据库操作Service实现
* @createDate 2026-05-06 11:16:24
*/
@Service
public class ArticleInfoServiceImpl extends ServiceImpl<ArticleInfoMapper, ArticleInfo>
    implements ArticleInfoService{

}




