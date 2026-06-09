package com.amadeus.lotterysystem.controller;


import com.amadeus.lotterysystem.common.pojo.CommonResult;
import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;
import com.amadeus.lotterysystem.service.DrawPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrawPrizeController {

    @Autowired
    private DrawPrizeService drawPrizeService;

    @RequestMapping("/draw-prize")
    public CommonResult<Boolean> drawPrize(@RequestBody @Validated DrawPrizeParam param){
        drawPrizeService.drawPrize(param);
        return CommonResult.success(true);
    }
}
