package com.amadeus.lotterysystem.controller.result;

import com.sun.source.doctree.SeeTree;
import lombok.Data;


@Data
public class BaseUserInfoResult {

    /**
     * 人员id
     */
    private Long userId;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 人员身份
     */
    private String identity;
}
