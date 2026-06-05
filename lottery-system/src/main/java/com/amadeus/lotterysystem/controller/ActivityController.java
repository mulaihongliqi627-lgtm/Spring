package com.amadeus.lotterysystem.controller;


import com.amadeus.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ControllerException;
import com.amadeus.lotterysystem.common.pojo.CommonResult;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.CreateActivityParam;
import com.amadeus.lotterysystem.controller.result.CreateActivityResult;

import com.amadeus.lotterysystem.service.ActivityService;
import com.amadeus.lotterysystem.service.dto.CreateActivityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {


    @Autowired
    private ActivityService activityService;

    @RequestMapping("/create")
    public CommonResult<CreateActivityResult> createActivity(@Validated @RequestBody CreateActivityParam param){
        log.info("创建抽奖活动 , param{}", JacksonUtil.writeValueAsString(param));
        return CommonResult.success(convertToCreateActivityResult(activityService.createActivity(param)));
    }

    public  CreateActivityResult convertToCreateActivityResult(CreateActivityDTO createActivityDTO){
        if(null == createActivityDTO){
            throw new ControllerException(ControllerErrorCodeConstants.CREATE_ACTIVITY_ERROR);
        }
        CreateActivityResult createActivityResult = new CreateActivityResult();
        createActivityResult.setActivityId(createActivityDTO.getActivityId());
        return createActivityResult;

    }

}
