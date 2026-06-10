package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.controller.param.DrawPrizeParam;
import com.amadeus.lotterysystem.dao.dataobject.ActivityDO;
import com.amadeus.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.amadeus.lotterysystem.dao.dataobject.PrizeDO;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.dataobject.WinningRecordDO;
import com.amadeus.lotterysystem.dao.mapper.ActivityMapper;
import com.amadeus.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.amadeus.lotterysystem.dao.mapper.PrizeMapper;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import com.amadeus.lotterysystem.dao.mapper.WinningRecordMapper;
import com.amadeus.lotterysystem.service.WinningRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WinningRecordServiceImpl implements WinningRecordService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WinningRecordMapper winningRecordMapper;

    @Override
    public void saveWinningRecords(DrawPrizeParam param) {
        ActivityDO activityDO = activityMapper.selectById(param.getActivityId());
        ActivityPrizeDO activityPrizeDO = activityPrizeMapper.selectByAPId(param.getActivityId(), param.getPrizeId());
        PrizeDO prizeDO = prizeMapper.selectById(param.getPrizeId());
        if (activityDO == null || activityPrizeDO == null || prizeDO == null) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_OR_PRIZE_IS_EMPTY);
        }

        List<Long> winnerIds = param.getWinnerList()
                .stream()
                .map(DrawPrizeParam.Winner::getUserId)
                .distinct()
                .collect(Collectors.toList());
        List<UserDO> winnerDOList = userMapper.selectBatchIds(winnerIds);
        if (CollectionUtils.isEmpty(winnerDOList) || winnerDOList.size() != winnerIds.size()) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERROR);
        }
        Map<Long, UserDO> winnerMap = winnerDOList.stream()
                .collect(Collectors.toMap(UserDO::getId, Function.identity()));

        for (DrawPrizeParam.Winner winner : param.getWinnerList()) {
            UserDO winnerDO = winnerMap.get(winner.getUserId());
            WinningRecordDO recordDO = new WinningRecordDO();
            recordDO.setActivityId(activityDO.getId());
            recordDO.setActivityName(activityDO.getActivityName());
            recordDO.setPrizeId(prizeDO.getId());
            recordDO.setPrizeName(prizeDO.getName());
            recordDO.setPrizeTier(activityPrizeDO.getPrizeTiers());
            recordDO.setWinnerId(winnerDO.getId());
            recordDO.setWinnerName(winnerDO.getUserName());
            recordDO.setWinnerEmail(winnerDO.getEmail());
            recordDO.setWinnerPhoneNumber(winnerDO.getPhoneNumber());
            recordDO.setWinningTime(param.getWinningTime());
            winningRecordMapper.insert(recordDO);
        }
    }
}
