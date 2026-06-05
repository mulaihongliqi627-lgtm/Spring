package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.CreatePrizeParam;
import com.amadeus.lotterysystem.controller.param.PageParam;
import com.amadeus.lotterysystem.service.dto.PageListDTO;
import com.amadeus.lotterysystem.service.dto.PrizeDTO;
import com.amadeus.lotterysystem.service.dto.PrizeListDTO;
import org.springframework.web.multipart.MultipartFile;



public interface PrizeService {

    /**
     * 创建奖品
     *
     * @param param
     * @param prizePic
     * @return
     */
    Long createPrize(CreatePrizeParam param, MultipartFile prizePic);

    PageListDTO<PrizeDTO> findPrizeList(PageParam param);
}
