package com.amadeus.lotterysystem.service.activitystatus;

import com.amadeus.lotterysystem.service.dto.ConvertActivityStatusDTO;

public interface ActivityStatusManager {

    void handlerEvent(ConvertActivityStatusDTO convertActivityStatusDTO);
}
