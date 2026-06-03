package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.ActivityUser;
import generator.service.ActivityUserService;
import generator.mapper.ActivityUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Amadeus
* @description 针对表【activity_user】的数据库操作Service实现
* @createDate 2026-05-29 16:56:11
*/
@Service
public class ActivityUserServiceImpl extends ServiceImpl<ActivityUserMapper, ActivityUser>
    implements ActivityUserService{

}




