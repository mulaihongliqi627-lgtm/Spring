package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;

public interface WinningRecordService {

    void saveWinningRecords(DrawPrizeParam param);
}
