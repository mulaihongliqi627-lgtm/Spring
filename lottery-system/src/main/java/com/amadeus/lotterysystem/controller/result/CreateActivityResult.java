package com.amadeus.lotterysystem.controller.result;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;


@Data
public class CreateActivityResult implements Serializable {


    @NotBlank(message = "活动id不能为空")
    private Long activityId;
}
