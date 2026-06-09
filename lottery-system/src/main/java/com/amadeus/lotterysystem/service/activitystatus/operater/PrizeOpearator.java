package com.amadeus.lotterysystem.service.activitystatus.operater;

import com.amadeus.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.amadeus.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.amadeus.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrizeOpearator extends AbstractActivityOperator {

    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Override
    public Integer sequence() {
        return 1;
    }

    @Override
    public Boolean needConvert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        return convertActivityStatusDTO != null
                && convertActivityStatusDTO.getActivityId() != null
                && convertActivityStatusDTO.getPrizeId() != null
                && convertActivityStatusDTO.getTargetPrizeStatus() != null;
    }

    @Override
    public Boolean convert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        ActivityPrizeStatusEnum targetStatus = convertActivityStatusDTO.getTargetPrizeStatus();
        ActivityPrizeDO activityPrizeDO = activityPrizeMapper.selectByAPId(
                convertActivityStatusDTO.getActivityId(),
                convertActivityStatusDTO.getPrizeId());
        if (activityPrizeDO == null) {
            return false;
        }
        if (targetStatus.name().equalsIgnoreCase(activityPrizeDO.getStatus())) {
            return true;
        }
        if (!ActivityPrizeStatusEnum.INIT.name().equalsIgnoreCase(activityPrizeDO.getStatus())) {
            return false;
        }

        ActivityPrizeDO updateDO = new ActivityPrizeDO();
        updateDO.setStatus(targetStatus.name());
        int updateRows = activityPrizeMapper.update(updateDO,
                new LambdaUpdateWrapper<ActivityPrizeDO>()
                        .eq(ActivityPrizeDO::getId, activityPrizeDO.getId())
                        .eq(ActivityPrizeDO::getStatus, ActivityPrizeStatusEnum.INIT.name()));
        return updateRows > 0 || verifyConverted(
                convertActivityStatusDTO.getActivityId(),
                convertActivityStatusDTO.getPrizeId(),
                targetStatus);
    }

    private boolean verifyConverted(Long activityId, Long prizeId, ActivityPrizeStatusEnum targetStatus) {
        ActivityPrizeDO activityPrizeDO = activityPrizeMapper.selectByAPId(activityId, prizeId);
        return activityPrizeDO != null && targetStatus.name().equalsIgnoreCase(activityPrizeDO.getStatus());
    }
}
