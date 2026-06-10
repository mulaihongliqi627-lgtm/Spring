package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;

public interface WinningRecordService {

    void saveWinningRecords(DrawPrizeParam param);

    boolean hasWinningRecords(Long activityId, Long prizeId);

    int deleteWinningRecords(Long activityId, Long prizeId);
}
