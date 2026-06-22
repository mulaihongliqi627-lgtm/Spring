package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.dao.dataobject.WinningRecordDO;
import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;

import java.util.List;

public interface WinningRecordService {

    void saveWinningRecords(DrawPrizeParam param);

    boolean hasWinningRecords(Long activityId, Long prizeId);

    int deleteWinningRecords(Long activityId, Long prizeId);

    List<WinningRecordDO> findWinningRecords(Long activityId, Long prizeId);
}
