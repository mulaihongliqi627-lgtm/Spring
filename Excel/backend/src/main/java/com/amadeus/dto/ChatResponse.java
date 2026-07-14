package com.amadeus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// 响应的实体类
@Data
@AllArgsConstructor
public class ChatResponse {

    // 文件名称
    private String fileName;

    // 文件内容
    private String previewText;

    // 大模型的回复
    private String aiAnswer;
}