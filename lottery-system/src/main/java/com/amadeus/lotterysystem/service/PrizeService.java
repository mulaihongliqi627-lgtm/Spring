package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.CreatePrizeParam;
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
}
