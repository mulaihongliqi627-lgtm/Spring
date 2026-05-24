package com.amadeus.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpBlogRequest {

    @NotNull
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
