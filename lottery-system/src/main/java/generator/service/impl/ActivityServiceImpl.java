package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Activity;
import generator.service.ActivityService;
import generator.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

/**
* @author Amadeus
* @description 针对表【activity】的数据库操作Service实现
* @createDate 2026-05-29 16:56:11
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService{

}




