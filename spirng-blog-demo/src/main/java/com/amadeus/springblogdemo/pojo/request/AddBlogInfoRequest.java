package com.amadeus.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddBlogInfoRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer userId;
}
