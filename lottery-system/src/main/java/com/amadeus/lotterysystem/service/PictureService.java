package com.amadeus.lotterysystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    //保存图片
    String savePicture(MultipartFile pic);
}
