package com.amadeus.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserByActivityParam {

    @NotNull(message = "人员id不能为空")
    private Long usreId;

    @NotBlank(message = "人员名称不能为空")
    private String userName;
}
