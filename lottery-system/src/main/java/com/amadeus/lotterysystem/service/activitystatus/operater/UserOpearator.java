package com.amadeus.lotterysystem.service.activitystatus.operater;

import com.amadeus.lotterysystem.dao.dataobject.ActivityUserDO;
import com.amadeus.lotterysystem.dao.mapper.ActivityUserMapper;
import com.amadeus.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.amadeus.lotterysystem.service.enums.ActivityUserStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserOpearator extends AbstractActivityOperator {

    @Autowired
    private ActivityUserMapper activityUserMapper;

    @Override
    public Integer sequence() {
        return 1;
    }

    @Override
    public Boolean needConvert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        return convertActivityStatusDTO != null
                && convertActivityStatusDTO.getActivityId() != null
                && convertActivityStatusDTO.getTargetUserStatus() != null
                && !distinctUserIds(convertActivityStatusDTO).isEmpty();
    }

    @Override
    public Boolean convert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        List<Long> userIds = distinctUserIds(convertActivityStatusDTO);
        if (userIds.isEmpty()) {
            return false;
        }

        ActivityUserStatusEnum targetStatus = convertActivityStatusDTO.getTargetUserStatus();
        List<ActivityUserDO> activityUserDOList = selectActivityUsers(
                convertActivityStatusDTO.getActivityId(), userIds);
        Set<Long> existUserIds = activityUserDOList.stream()
                .map(ActivityUserDO::getUserId)
                .collect(Collectors.toSet());
        if (!existUserIds.containsAll(userIds)) {
            return false;
        }

        List<ActivityUserDO> needUpdateUsers = activityUserDOList.stream()
                .filter(activityUserDO -> !targetStatus.name().equalsIgnoreCase(activityUserDO.getStatus()))
                .collect(Collectors.toList());
        if (needUpdateUsers.isEmpty()) {
            return true;
        }
        boolean hasInvalidStatus = needUpdateUsers.stream()
                .anyMatch(activityUserDO -> !ActivityUserStatusEnum.INIT.name()
                        .equalsIgnoreCase(activityUserDO.getStatus()));
        if (hasInvalidStatus) {
            return false;
        }

        List<Long> needUpdateUserIds = needUpdateUsers.stream()
                .map(ActivityUserDO::getUserId)
                .distinct()
                .collect(Collectors.toList());
        ActivityUserDO updateDO = new ActivityUserDO();
        updateDO.setStatus(targetStatus.name());
        activityUserMapper.update(updateDO,
                new LambdaUpdateWrapper<ActivityUserDO>()
                        .eq(ActivityUserDO::getActivityId, convertActivityStatusDTO.getActivityId())
                        .in(ActivityUserDO::getUserId, needUpdateUserIds)
                        .eq(ActivityUserDO::getStatus, ActivityUserStatusEnum.INIT.name()));

        return verifyConverted(convertActivityStatusDTO.getActivityId(), userIds, targetStatus);
    }

    private List<Long> distinctUserIds(ConvertActivityStatusDTO convertActivityStatusDTO) {
        if (convertActivityStatusDTO.getUserIds() == null) {
            return List.of();
        }
        return convertActivityStatusDTO.getUserIds().stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<ActivityUserDO> selectActivityUsers(Long activityId, List<Long> userIds) {
        return activityUserMapper.selectList(new LambdaQueryWrapper<ActivityUserDO>()
                .eq(ActivityUserDO::getActivityId, activityId)
                .in(ActivityUserDO::getUserId, userIds));
    }

    private boolean verifyConverted(Long activityId,
                                    List<Long> userIds,
                                    ActivityUserStatusEnum targetStatus) {
        List<ActivityUserDO> activityUserDOList = selectActivityUsers(activityId, userIds);
        Set<Long> existUserIds = activityUserDOList.stream()
                .map(ActivityUserDO::getUserId)
                .collect(Collectors.toSet());
        return existUserIds.containsAll(userIds)
                && activityUserDOList.stream()
                .allMatch(activityUserDO -> targetStatus.name().equalsIgnoreCase(activityUserDO.getStatus()));
    }
}
