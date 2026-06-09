package com.amadeus.lotterysystem.controller;


import com.amadeus.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ControllerException;
import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.common.pojo.CommonResult;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.CreateActivityParam;
import com.amadeus.lotterysystem.controller.param.PageParam;
import com.amadeus.lotterysystem.controller.result.CreateActivityResult;

import com.amadeus.lotterysystem.controller.result.FindActivityListResult;
import com.amadeus.lotterysystem.controller.result.GetActivityDetailResult;
import com.amadeus.lotterysystem.service.ActivityService;
import com.amadeus.lotterysystem.service.dto.ActivityDTO;
import com.amadeus.lotterysystem.service.dto.ActivityDetailDTO;
import com.amadeus.lotterysystem.service.dto.CreateActivityDTO;
import com.amadeus.lotterysystem.service.dto.PageListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.stream.Collectors;

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


    @RequestMapping("/find-list")
    public CommonResult<FindActivityListResult> findActivityList(PageParam param){
        log.info("查询活动列表 , param{}",JacksonUtil.writeValueAsString(param));
        PageListDTO<ActivityDTO> findActivityList = activityService.findActivityList(param);
        return CommonResult.success(convertToFindActivityResult(findActivityList));
    }


    @RequestMapping("/detail/find")
    public CommonResult<GetActivityDetailResult> getActivityDetailResult(@RequestParam("activityId") Long activityId){
        log.info("查询活动详情 , activityId{}",activityId);
        ActivityDetailDTO activityDetailDTO =  activityService.getActivityDetail(activityId);
        return CommonResult.success(convertToGetActivityDetailResult(activityDetailDTO));
    }

    private FindActivityListResult convertToFindActivityResult(PageListDTO<ActivityDTO> activityList) {
        if(null == activityList){
            throw  new ServiceException(ControllerErrorCodeConstants.FIND_ACTIVITY_LIST_ERROR);
        }
        FindActivityListResult result = new FindActivityListResult();
        result.setTotal(activityList.getTotal());
        result.setRecords(activityList.getRecords()
                .stream()
                .map(activityDTO -> {
                    FindActivityListResult.ActivityInfo activityInfo = new FindActivityListResult.ActivityInfo();
                    activityInfo.setActivityId(activityDTO.getActivityId());
                    activityInfo.setActivityName(activityDTO.getActivityName());
                    activityInfo.setDescription(activityDTO.getDescription());
                    activityInfo.setValid(activityDTO.valid());
                    return activityInfo;
                }).collect(Collectors.toList()));
        return result;
    }

    private GetActivityDetailResult convertToGetActivityDetailResult(ActivityDetailDTO detailDTO) {
        if (detailDTO == null) {
            throw new ControllerException(ControllerErrorCodeConstants.GET_ACTIVITY_DETAIL_ERROR);
        }

        GetActivityDetailResult result = new GetActivityDetailResult();
        result.setActivityId(detailDTO.getActivityId());
        result.setActivityName(detailDTO.getActivityName());
        result.setDesc(detailDTO.getDesc());
        result.setValid(detailDTO.valid());
        result.setPrizeList((detailDTO.getPrizeDTOList() == null ? Collections.<ActivityDetailDTO.PrizeDTO>emptyList() : detailDTO.getPrizeDTOList())
                .stream()
                .map(prizeDTO -> {
                    GetActivityDetailResult.PrizeInfo prizeInfo = new GetActivityDetailResult.PrizeInfo();
                    prizeInfo.setPrizeId(prizeDTO.getPrizeId());
                    prizeInfo.setName(prizeDTO.getName());
                    prizeInfo.setImageUrl(prizeDTO.getImageUrl());
                    prizeInfo.setPrice(prizeDTO.getPrice());
                    prizeInfo.setDescription(prizeDTO.getDescription());
                    prizeInfo.setTiers(prizeDTO.getTiers() == null ? null : prizeDTO.getTiers().name());
                    prizeInfo.setPrizeAmount(prizeDTO.getPrizeAmount());
                    prizeInfo.setValid(prizeDTO.valid());
                    return prizeInfo;
                }).collect(Collectors.toList()));
        result.setUserList((detailDTO.getUserDTOList() == null ? Collections.<ActivityDetailDTO.UserDTO>emptyList() : detailDTO.getUserDTOList())
                .stream()
                .map(userDTO -> {
                    GetActivityDetailResult.UserInfo userInfo = new GetActivityDetailResult.UserInfo();
                    userInfo.setUserId(userDTO.getUserId());
                    userInfo.setUserName(userDTO.getUserName());
                    userInfo.setValid(userDTO.valid());
                    return userInfo;
                }).collect(Collectors.toList()));
        return result;
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
