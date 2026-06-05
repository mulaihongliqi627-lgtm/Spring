package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.controller.param.CreatePrizeParam;
import com.amadeus.lotterysystem.controller.param.PageParam;
import com.amadeus.lotterysystem.dao.dataobject.PrizeDO;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.PrizeMapper;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import com.amadeus.lotterysystem.service.PictureService;
import com.amadeus.lotterysystem.service.PrizeService;
import com.amadeus.lotterysystem.service.dto.PageListDTO;
import com.amadeus.lotterysystem.service.dto.PrizeDTO;
import com.amadeus.lotterysystem.service.dto.PrizeListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public PageListDTO<PrizeDTO> findPrizeList(PageParam pageParam) {
        long total = prizeMapper.count();

        //查询当前列表中的数据
        List<PrizeDO> prizeDOList = prizeMapper.findPrizeList(pageParam.offest(), pageParam.getPageSize());

        List<PrizeDTO> prizeDTOList = new ArrayList<>();

        //把后端查询出的列表数据构建DTO列表
        for(PrizeDO prizeDO : prizeDOList){
            PrizeDTO prizeDTO = new PrizeDTO();
            prizeDTO.setPrizeId(prizeDO.getId());
            prizeDTO.setName(prizeDO.getName());
            prizeDTO.setDescription(prizeDO.getDescription());
            prizeDTO.setPrice(prizeDO.getPrice());
            prizeDTO.setImageUrl(prizeDO.getImageUrl());
            prizeDTOList.add(prizeDTO);
        }

        //构建返回数据
        return new PageListDTO<>(total,prizeDTOList);
    }



}
