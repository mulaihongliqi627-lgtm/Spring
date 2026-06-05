package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.CreateActivityParam;
import com.amadeus.lotterysystem.service.dto.CreateActivityDTO;

public interface ActivityService {
    CreateActivityDTO createActivity(CreateActivityParam param);
}
