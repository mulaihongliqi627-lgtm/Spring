package com.amadeus.lotterysystem.service.mq;

import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;
import com.amadeus.lotterysystem.service.DrawPrizeService;
import com.amadeus.lotterysystem.service.WinningRecordService;
import com.amadeus.lotterysystem.service.activitystatus.ActivityStatusManager;
import com.amadeus.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityUserStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

import static com.amadeus.lotterysystem.common.config.DirectRabbitConfig.QUEUE_NAME;

@Slf4j
@Component
@RabbitListener(queues = QUEUE_NAME)
public class MqReceiver {

    @Autowired
    private DrawPrizeService drawPrizeService;

    @Autowired
    private ActivityStatusManager activityStatusManager;

    @Autowired
    private WinningRecordService winningRecordService;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void process(Map<String, String> message) {
        log.info("MQ received message: {}", JacksonUtil.writeValueAsString(message));
        String paramString = message.get("messageData");
        DrawPrizeParam drawPrizeParam = JacksonUtil.readValue(paramString, DrawPrizeParam.class);

        try {
            if (!drawPrizeService.checkDrawPrizeParam(drawPrizeParam)) {
                return;
            }
            winningRecordService.saveWinningRecords(drawPrizeParam);
            statusConvert(drawPrizeParam);
        } catch (ServiceException e) {
            log.info("handle MQ message failed! code={}, msg={}", e.getCode(), e.getErrorMsg());
            throw e;
        } catch (Exception e) {
            log.info("handle MQ message failed!", e);
            throw new RuntimeException(e);
        }
    }

    private void statusConvert(DrawPrizeParam param) {
        ConvertActivityStatusDTO convertActivityStatusDTO = new ConvertActivityStatusDTO();
        convertActivityStatusDTO.setActivityId(param.getActivityId());
        convertActivityStatusDTO.setPrizeId(param.getPrizeId());
        convertActivityStatusDTO.setTargetPrizeStatus(ActivityPrizeStatusEnum.COMPLETED);
        convertActivityStatusDTO.setUserIds(param.getWinnerList()
                .stream()
                .map(DrawPrizeParam.Winner::getUserId)
                .collect(Collectors.toList()));
        convertActivityStatusDTO.setTargetUserStatus(ActivityUserStatusEnum.COMPLETED);
        convertActivityStatusDTO.setTargetActivityStatus(ActivityStatusEnum.COMPLETED);
        activityStatusManager.handlerEvent(convertActivityStatusDTO);
    }
}
