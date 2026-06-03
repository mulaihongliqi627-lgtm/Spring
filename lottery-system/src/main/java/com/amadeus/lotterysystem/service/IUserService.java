package com.amadeus.lotterysystem.service;


import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<UserDO> {
    // 这里可以定义自定义业务方法
    // BaseMapper 的 CRUD 方法已经自动继承
}
