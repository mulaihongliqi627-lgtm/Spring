package com.amadeus.lotterysystem.service.Impl;


import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;
import com.amadeus.lotterysystem.dao.dataobject.ActivityDO;
import com.amadeus.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.amadeus.lotterysystem.dao.mapper.ActivityMapper;
import com.amadeus.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.amadeus.lotterysystem.service.DrawPrizeService;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityStatusEnum;
import generator.service.ActivityPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.amadeus.lotterysystem.common.config.DirectRabbitConfig.EXCHANGE_NAME;
import static com.amadeus.lotterysystem.common.config.DirectRabbitConfig.ROUTING;


@Slf4j
@Service
public class DrawPrizeServiceImpl implements DrawPrizeService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ActivityMapper activityMapper;


    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Override
    public void drawPrize(DrawPrizeParam param) {
        Map<String, String> map = new HashMap<>();
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = JacksonUtil.writeValueAsString(param);
        map.put("messageId", messageId);
        map.put("messageData", messageData);

        // 发消息
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING, map);
        log.info("发送消息内容：{}", messageData);



    }

    @Override
    public Boolean checkDrawPrizeParam(DrawPrizeParam param) {
        ActivityDO activityDO = activityMapper.selectById(param.getActivityId());
        ActivityPrizeDO activityPrizeDO = activityPrizeMapper.selectByAPId(param.getActivityId(), param.getPrizeId());

        // 活动或奖品是否存在
        if (null == activityDO || null == activityPrizeDO) {
            // throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_OR_PRIZE_IS_EMPTY);
            log.info("校验抽奖请求失败！失败原因：{}",
                    ServiceErrorCodeConstants.ACTIVITY_OR_PRIZE_IS_EMPTY.getMsg());
            return false;
        }

        // 活动是否有效
        if (activityDO.getStatus()
                .equalsIgnoreCase(ActivityStatusEnum.COMPLETED.name())) {
            // throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_COMPLETED);
            log.info("校验抽奖请求失败！失败原因：{}",
                    ServiceErrorCodeConstants.ACTIVITY_COMPLETED.getMsg());
            return false;
        }

        // 奖品是否有效
        if (activityPrizeDO.getStatus()
                .equalsIgnoreCase(ActivityPrizeStatusEnum.COMPLETED.name())) {
            // throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_COMPLETED);
            log.info("校验抽奖请求失败！失败原因：{}",
                    ServiceErrorCodeConstants.ACTIVITY_PRIZE_COMPLETED.getMsg());
            return false;
        }

        // 中奖者人数是否和设置奖品数量一致 3 2
        if (activityPrizeDO.getPrizeAmount() != param.getWinnerList().size()) {
            // throw new ServiceException(ServiceErrorCodeConstants.WINNER_PRIZE_AMOUNT_ERROR);
            log.info("校验抽奖请求失败！失败原因：{}",
                    ServiceErrorCodeConstants.WINNER_PRIZE_AMOUNT_ERROR.getMsg());
            return false;
        }
        return true;
    }
}
