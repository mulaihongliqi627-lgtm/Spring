package com.amadeus.lotterysystem.controller;


import com.amadeus.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ControllerException;
import com.amadeus.lotterysystem.common.pojo.CommonResult;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.CreatePrizeParam;
import com.amadeus.lotterysystem.controller.param.PageParam;
import com.amadeus.lotterysystem.controller.result.FindPrizeListResult;
import com.amadeus.lotterysystem.service.PictureService;
import com.amadeus.lotterysystem.service.PrizeService;
import com.amadeus.lotterysystem.service.dto.PageListDTO;
import com.amadeus.lotterysystem.service.dto.PrizeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping("/prize")
public class PrizeController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PrizeService prizeService;

    @RequestMapping("/pic/upload")
    public String upLoadPicture(@RequestParam("file") MultipartFile file){
        return pictureService.savePicture(file);
    }


    /**
     * 创建奖品,参数以表单形式接收
     * @param param
     * @param picFile
     * @return
     */
    @RequestMapping("/create")
    public CommonResult<Long> createPrize(@Validated @RequestPart("param") CreatePrizeParam param,
                                          @RequestPart("prizePic") MultipartFile picFile){

        log.info("创建奖品请求,param={},picFile={}", JacksonUtil.writeValueAsString(param),picFile);
        return CommonResult.success(prizeService.createPrize(param,picFile));
    }

    @RequestMapping("/find-list")
    public CommonResult<FindPrizeListResult> findPrizeList(PageParam pageParam){
        log.info("查询奖品列表{}",JacksonUtil.writeValueAsString(pageParam));

        PageListDTO<PrizeDTO> pageListDTO = prizeService.findPrizeList(pageParam);
        return CommonResult.success(converToFindPrizeListResult(pageListDTO));

    }

    private FindPrizeListResult converToFindPrizeListResult(PageListDTO<PrizeDTO> pageListDTO) {
        if(null == pageListDTO){
            throw new ControllerException(ControllerErrorCodeConstants.FIND_PRIZE_LIST_ERROR);
        }
        FindPrizeListResult result = new FindPrizeListResult();
        result.setTotal(pageListDTO.getTotal());
        result.setRecords(pageListDTO.getRecords().stream()
                .map(prizeDTO -> {
                    FindPrizeListResult.PrizeInfo prizeInfo = new FindPrizeListResult.PrizeInfo();
                    prizeInfo.setPrizeId(prizeDTO.getPrizeId());
                    prizeInfo.setPrizeName(prizeDTO.getName());
                    prizeInfo.setDescription(prizeDTO.getDescription());
                    prizeInfo.setPrice(prizeDTO.getPrice());
                    prizeInfo.setImageUrl(prizeDTO.getImageUrl());
                    return prizeInfo;
                }
        ).toList());
        return result;
    }

}
