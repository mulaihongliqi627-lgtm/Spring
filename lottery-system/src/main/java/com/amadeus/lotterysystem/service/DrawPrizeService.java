package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;

public interface DrawPrizeService {

    void drawPrize(DrawPrizeParam param);

    Boolean checkDrawPrizeParam(DrawPrizeParam param);
}
