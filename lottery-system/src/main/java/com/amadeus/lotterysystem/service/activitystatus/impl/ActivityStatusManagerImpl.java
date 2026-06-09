package com.amadeus.lotterysystem.service.activitystatus.impl;

import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.service.ActivityService;
import com.amadeus.lotterysystem.service.activitystatus.ActivityStatusManager;
import com.amadeus.lotterysystem.service.activitystatus.operater.AbstractActivityOperator;
import com.amadeus.lotterysystem.service.dto.ConvertActivityStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ActivityStatusManagerImpl implements ActivityStatusManager {

    private final List<AbstractActivityOperator> operatorList;

    private final ActivityService activityService;

    public ActivityStatusManagerImpl(List<AbstractActivityOperator> operatorList,
                                     ActivityService activityService) {
        this.operatorList = operatorList.stream()
                .sorted(Comparator.comparing(AbstractActivityOperator::sequence))
                .collect(Collectors.toList());
        this.activityService = activityService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlerEvent(ConvertActivityStatusDTO convertActivityStatusDTO) {
        if (convertActivityStatusDTO == null || convertActivityStatusDTO.getActivityId() == null) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_STATUS_CONVERT_ERROR);
        }
        if (CollectionUtils.isEmpty(operatorList)) {
            log.error("activity status operator list is empty");
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_STATUS_CONVERT_ERROR);
        }

        boolean updated = false;
        for (AbstractActivityOperator operator : operatorList) {
            if (!Boolean.TRUE.equals(operator.needConvert(convertActivityStatusDTO))) {
                continue;
            }
            Boolean success = operator.convert(convertActivityStatusDTO);
            if (!Boolean.TRUE.equals(success)) {
                log.error("activity status convert failed, operator={}, dto={}",
                        operator.getClass().getSimpleName(), convertActivityStatusDTO);
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_STATUS_CONVERT_ERROR);
            }
            updated = true;
        }

        if (updated) {
            activityService.cacheActivity(convertActivityStatusDTO.getActivityId());
        }
    }
}
