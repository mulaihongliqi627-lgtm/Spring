package com.amadeus.lotterysystem.controller;

import com.amadeus.lotterysystem.common.pojo.CommonResult;
import com.amadeus.lotterysystem.controller.param.ShowWinningRecordParam;
import com.amadeus.lotterysystem.controller.result.WinningRecordResult;
import com.amadeus.lotterysystem.dao.dataobject.WinningRecordDO;
import com.amadeus.lotterysystem.service.WinningRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/winning-records")
public class WinningRecordController {

    @Autowired
    private WinningRecordService winningRecordService;

    @RequestMapping("/show")
    public CommonResult<List<WinningRecordResult>> showWinningRecords(@RequestBody ShowWinningRecordParam param) {
        List<WinningRecordDO> winningRecordDOList = winningRecordService.findWinningRecords(
                param.getActivityId(),
                param.getPrizeId());
        if (CollectionUtils.isEmpty(winningRecordDOList)) {
            return CommonResult.success(Collections.emptyList());
        }
        return CommonResult.success(winningRecordDOList.stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }

    private WinningRecordResult convert(WinningRecordDO winningRecordDO) {
        WinningRecordResult result = new WinningRecordResult();
        result.setActivityId(winningRecordDO.getActivityId());
        result.setPrizeId(winningRecordDO.getPrizeId());
        result.setPrizeName(winningRecordDO.getPrizeName());
        result.setPrizeTier(winningRecordDO.getPrizeTier());
        result.setWinnerId(winningRecordDO.getWinnerId());
        result.setWinnerName(winningRecordDO.getWinnerName());
        result.setWinningTime(winningRecordDO.getWinningTime());
        return result;
    }
}
