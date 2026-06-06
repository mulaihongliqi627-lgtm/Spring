package com.amadeus.lotterysystem.service.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityUserStatusEnum {
    INIT(1,"初始化"),
    CPMPLETED(2,"已被抽取");


    private final Integer code;


    private final String message;

    //根据name获取枚举类型

    public static ActivityUserStatusEnum forName(String name){
        for(ActivityUserStatusEnum activityUserStatusEnum : ActivityUserStatusEnum.values()){
            if(activityUserStatusEnum.name().equalsIgnoreCase(name)){
                return activityUserStatusEnum;
            }
        }
        return null;
    }
}
