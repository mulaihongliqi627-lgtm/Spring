package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.CreateActivityParam;
import com.amadeus.lotterysystem.controller.param.PageParam;
import com.amadeus.lotterysystem.service.dto.ActivityDTO;
import com.amadeus.lotterysystem.service.dto.CreateActivityDTO;
import com.amadeus.lotterysystem.service.dto.PageListDTO;

public interface ActivityService {
    CreateActivityDTO createActivity(CreateActivityParam param);

    PageListDTO<ActivityDTO> findActivityList(PageParam param);
}
