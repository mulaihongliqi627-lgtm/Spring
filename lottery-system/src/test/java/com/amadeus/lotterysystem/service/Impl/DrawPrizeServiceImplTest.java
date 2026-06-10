package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;
import com.amadeus.lotterysystem.service.DrawPrizeService;
import com.amadeus.lotterysystem.service.activitystatus.ActivityStatusManager;
import com.amadeus.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityUserStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DrawPrizeServiceImplTest {

    @Autowired
    private DrawPrizeService drawPrizeService;


    @Autowired
    private ActivityStatusManager activityStatusManager;

    @Test
    public void drawPrize() {
        DrawPrizeParam param = new DrawPrizeParam();
        param.setActivityId(1L);
        param.setPrizeId(1L);
        param.setPrizeTiers("FIRST_PRIZE");
        param.setWinningTime(new Date());
        List<DrawPrizeParam.Winner> winnerList = new ArrayList<>();
        DrawPrizeParam.Winner winner = new DrawPrizeParam.Winner();
        winner.setUserId(1L);
        winner.setUserName("张三");
        winnerList.add(winner);
        param.setWinnerList(winnerList);
        drawPrizeService.drawPrize(param);

    }


    @Test
    public void testConvert(){
        ConvertActivityStatusDTO convertActivityStatusDTO = new ConvertActivityStatusDTO();
        convertActivityStatusDTO.setActivityId(24L);
        convertActivityStatusDTO.setTargetActivityStatus(ActivityStatusEnum.COMPLETED);
        convertActivityStatusDTO.setPrizeId(18L);
        convertActivityStatusDTO.setTargetPrizeStatus(ActivityPrizeStatusEnum.COMPLETED);
        List<Long> userIds = Arrays.asList(1L);
        convertActivityStatusDTO.setUserIds(userIds);
        convertActivityStatusDTO.setTargetUserStatus(ActivityUserStatusEnum.COMPLETED);
        activityStatusManager.handlerEvent(convertActivityStatusDTO);

    }

}