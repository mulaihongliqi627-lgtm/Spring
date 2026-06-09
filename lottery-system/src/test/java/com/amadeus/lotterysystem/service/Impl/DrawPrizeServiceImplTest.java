package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;
import com.amadeus.lotterysystem.service.DrawPrizeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DrawPrizeServiceImplTest {

    @Autowired
    private DrawPrizeService drawPrizeService;

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

}