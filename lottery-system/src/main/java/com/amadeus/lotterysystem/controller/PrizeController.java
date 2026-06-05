package com.amadeus.lotterysystem.controller;


import com.amadeus.lotterysystem.service.PictureService;
import generator.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/prize")
public class PrizeController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    public String upLoadPicture(MultipartFile file){
        return pictureService.savePicture(file);
    }

}
