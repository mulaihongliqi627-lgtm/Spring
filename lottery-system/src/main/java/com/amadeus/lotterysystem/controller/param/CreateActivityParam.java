package com.amadeus.lotterysystem.controller.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class CreateActivityParam implements Serializable {


    @NotBlank(message = "活动名称不能为空")
    private String activityName;


    @NotBlank(message = "活动描述不能为空")
    private String description;


    @Valid
    @NotEmpty(message = "奖品列表不能为空")
    private List<CreatePrizeByActivityParam> acticityPrizeList;


    @Valid
    @NotEmpty(message = "人员列表不能为空")
    private List<CreateUserByActivityParam> activityUserList;
}
