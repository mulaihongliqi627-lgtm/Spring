package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.controller.param.CreatePrizeParam;
import com.amadeus.lotterysystem.dao.dataobject.PrizeDO;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.PrizeMapper;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import com.amadeus.lotterysystem.service.PictureService;
import com.amadeus.lotterysystem.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class PrizeServiceImpl implements PrizeService {


    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    private PictureService pictureService;

    @Override
    public Long createPrize(CreatePrizeParam param, MultipartFile prizePic) {

        PrizeDO prizeDO = new PrizeDO();
        prizeDO.setName(param.getPrizeName());
        prizeDO.setDescription(param.getDescription());
        prizeDO.setPrice(param.getPrice());

        //保存图片
        String fileName = pictureService.savePicture(prizePic);
        prizeDO.setImageUrl(fileName);

        prizeMapper.insert(prizeDO);

        return prizeDO.getId();

    }


}
