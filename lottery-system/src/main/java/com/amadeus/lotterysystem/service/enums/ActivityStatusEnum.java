package com.amadeus.lotterysystem.service.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityStatusEnum {

    RUNNING(1, "进行中"),
    COMPLETED(2, "已结束");

    private final Integer code;

    private final String message;


    public static ActivityStatusEnum forName(String name) {
        for (ActivityStatusEnum activityStatusEnum : ActivityStatusEnum.values()) {
            if (activityStatusEnum.name().equalsIgnoreCase(name)) {
                return activityStatusEnum;
            }
        }
        return null;
    }
}
