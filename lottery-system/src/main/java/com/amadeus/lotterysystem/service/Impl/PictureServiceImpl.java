package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    /**
     * 图片保存路径
     */
    @Value("${pic.local-path}")
    private String localPath;

    @Override
    public String savePicture(MultipartFile multipartFile) {

        //创建目录
        File dir = new File(localPath);
        if (!dir.exists()) {
            dir.mkdirs();//父级目录不存在时也会顺带创建父级目录
        }

        //创建索引
        String filename = multipartFile.getOriginalFilename();
        assert filename != null;
        //获取图片类型  jpg / png
        String suffix = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID() + suffix;//索引

        //图片保存
        try {
            multipartFile.transferTo(new File(dir, filename));
        } catch (IOException e) {
            throw  new ServiceException(ServiceErrorCodeConstants.PIC_UPLOAD_ERROR);
        }
        return filename;
    }
}
