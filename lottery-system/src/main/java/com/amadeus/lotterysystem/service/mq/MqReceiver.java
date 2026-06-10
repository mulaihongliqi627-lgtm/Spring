package com.amadeus.lotterysystem.service.mq;

import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;
import com.amadeus.lotterysystem.service.DrawPrizeMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.amadeus.lotterysystem.common.config.DirectRabbitConfig.QUEUE_NAME;

@Slf4j
@Component
@RabbitListener(queues = QUEUE_NAME)
public class MqReceiver {

    @Autowired
    private DrawPrizeMessageService drawPrizeMessageService;

    @RabbitHandler
    public void process(Map<String, String> message) {
        log.info("MQ received message: {}", JacksonUtil.writeValueAsString(message));
        String paramString = message.get("messageData");
        DrawPrizeParam drawPrizeParam = JacksonUtil.readValue(paramString, DrawPrizeParam.class);

        try {
            drawPrizeMessageService.handleDrawPrize(drawPrizeParam);
        } catch (ServiceException e) {
            log.info("handle MQ message failed! code={}, msg={}", e.getCode(), e.getErrorMsg());
            throw e;
        } catch (Exception e) {
            log.info("handle MQ message failed!", e);
            throw new RuntimeException(e);
        }
    }
}
