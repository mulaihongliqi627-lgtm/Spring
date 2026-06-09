package com.amadeus.lotterysystem.service.activitystatus.operater;

import com.amadeus.lotterysystem.dao.dataobject.ActivityDO;
import com.amadeus.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.amadeus.lotterysystem.dao.mapper.ActivityMapper;
import com.amadeus.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.amadeus.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityOperator extends AbstractActivityOperator {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Override
    public Integer sequence() {
        return 2;
    }

    @Override
    public Boolean needConvert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        if (convertActivityStatusDTO == null
                || convertActivityStatusDTO.getActivityId() == null
                || convertActivityStatusDTO.getTargetActivityStatus() == null) {
            return false;
        }

        if (!ActivityStatusEnum.COMPLETED.equals(convertActivityStatusDTO.getTargetActivityStatus())) {
            return true;
        }

        Long totalPrizeCount = activityPrizeMapper.selectCount(
                new LambdaQueryWrapper<ActivityPrizeDO>()
                        .eq(ActivityPrizeDO::getActivityId, convertActivityStatusDTO.getActivityId()));
        Long unfinishedPrizeCount = activityPrizeMapper.selectCount(
                new LambdaQueryWrapper<ActivityPrizeDO>()
                        .eq(ActivityPrizeDO::getActivityId, convertActivityStatusDTO.getActivityId())
                        .ne(ActivityPrizeDO::getStatus, ActivityPrizeStatusEnum.COMPLETED.name()));
        return totalPrizeCount > 0 && unfinishedPrizeCount == 0;
    }

    @Override
    public Boolean convert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        ActivityStatusEnum targetStatus = convertActivityStatusDTO.getTargetActivityStatus();
        ActivityDO activityDO = activityMapper.selectById(convertActivityStatusDTO.getActivityId());
        if (activityDO == null) {
            return false;
        }
        if (targetStatus.name().equalsIgnoreCase(activityDO.getStatus())) {
            return true;
        }
        if (!ActivityStatusEnum.RUNNING.name().equalsIgnoreCase(activityDO.getStatus())) {
            return false;
        }

        ActivityDO updateDO = new ActivityDO();
        updateDO.setStatus(targetStatus.name());
        int updateRows = activityMapper.update(updateDO,
                new LambdaUpdateWrapper<ActivityDO>()
                        .eq(ActivityDO::getId, activityDO.getId())
                        .eq(ActivityDO::getStatus, ActivityStatusEnum.RUNNING.name()));
        return updateRows > 0 || verifyConverted(activityDO.getId(), targetStatus);
    }

    private boolean verifyConverted(Long activityId, ActivityStatusEnum targetStatus) {
        ActivityDO activityDO = activityMapper.selectById(activityId);
        return activityDO != null && targetStatus.name().equalsIgnoreCase(activityDO.getStatus());
    }
}
