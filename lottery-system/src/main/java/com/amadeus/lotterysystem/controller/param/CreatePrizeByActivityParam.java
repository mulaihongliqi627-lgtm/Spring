package com.amadeus.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePrizeByActivityParam {

    @NotNull(message = "奖品id不能为空")
    private Long prizeId;

    @NotNull(message = "奖品数量不能为空")
    @Positive(message = "奖品数量必须大于0")
    private Long prizeAmount;

    @NotBlank(message = "奖品等级不能为空")
    private String prizeTiers;
}
