package com.amadeus.lotterysystem.dao.dataobject;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("prize")
public class PrizeDO extends BaseDO implements Serializable {

    /**
     * 奖品名称
     */
    private String name;


    /**
     * 奖品描述
     */
    private String description;

    /**
     * 奖品金额
     */
    private BigDecimal price;

    /**
     * 奖品图片
     */
    private String imageUrl;
}
