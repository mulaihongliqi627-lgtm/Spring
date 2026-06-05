package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.controller.param.CreateActivityParam;
import com.amadeus.lotterysystem.controller.param.CreatePrizeByActivityParam;
import com.amadeus.lotterysystem.controller.param.CreateUserByActivityParam;
import com.amadeus.lotterysystem.dao.mapper.ActivityMapper;
import com.amadeus.lotterysystem.dao.mapper.PrizeMapper;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import com.amadeus.lotterysystem.service.ActivityService;
import com.amadeus.lotterysystem.service.dto.CreateActivityDTO;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrizeMapper prizeMapper;

    @Override
    public CreateActivityDTO createActivity(CreateActivityParam param) {
        //校验活动参数
        checkActivityParam(param);

        return null;

    }

    private void checkActivityParam(CreateActivityParam param) {
        if (null == param
                || CollectionUtils.isEmpty(param.getActivityUserList())
                || CollectionUtils.isEmpty(param.getActicityPrizeList())) {
            throw new ServiceException(ServiceErrorCodeConstants.CREATE_ACTIVITY_INFO_IS_EMPTY);
        }

        if (param.getActivityUserList().stream()
                .anyMatch(user -> user == null || user.getUsreId() == null)) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
        }

        if (param.getActicityPrizeList().stream()
                .anyMatch(prize -> prize == null || prize.getPrizeId() == null)) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERROR);
        }

        if (param.getActicityPrizeList().stream()
                .anyMatch(prize -> prize.getPrizeAmount() == null || prize.getPrizeAmount() <= 0)) {
            throw new ServiceException(ServiceErrorCodeConstants.USER_PRIZE_AMOUNT_ERROR);
        }

        // 人员id在人员表中是否存在
        List<Long> activityUserIds = param.getActivityUserList()
                .stream()
                .map(CreateUserByActivityParam::getUsreId)
                .distinct()
                .collect(Collectors.toList());

        List<Long> selectedUserIds = userMapper.selectByIds(activityUserIds);
        if (CollectionUtils.isEmpty(selectedUserIds)) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
        }
        for(Long activityUserId : activityUserIds){
            if(!selectedUserIds.contains(activityUserId)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
            }
        }

        // 奖品id在奖品表中是否存在
        List<Long> activityPrizeIds = param.getActicityPrizeList()
                .stream()
                .map(CreatePrizeByActivityParam :: getPrizeId)
                .distinct()
                .collect(Collectors.toList());

        List<Long> selectedPrizeIds = prizeMapper.selectExistIdByIds(activityPrizeIds);
        if (CollectionUtils.isEmpty(selectedPrizeIds)) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERROR);
        }
        for(Long activityPrizeId : activityPrizeIds){
            if(!selectedPrizeIds.contains(activityPrizeId)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERROR);
            }
        }

        // 人员数量大于等于奖品数量
        int userAmount = param.getActivityUserList().size();
        Long prizeAmount = param.getActicityPrizeList()
                .stream()
                .mapToLong(CreatePrizeByActivityParam::getPrizeAmount)
                .sum();
        if(userAmount < prizeAmount){
            throw new ServiceException(ServiceErrorCodeConstants.USER_PRIZE_AMOUNT_ERROR);
        }

        // 校验活动奖品等奖有效性
        param.getActicityPrizeList().forEach(prize -> {
            if (null == ActivityPrizeTiersEnum.forName(prize.getPrizeTiers())) {
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_TIERS_ERROR);
            }
        });


    }
}
