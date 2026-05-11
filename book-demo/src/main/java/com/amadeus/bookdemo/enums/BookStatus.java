package com.amadeus.bookdemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.datatransfer.Clipboard;

@Getter
@AllArgsConstructor
public enum BookStatus {
    DELETED(0,"无效"),
    NORMAL(1,"可借阅"),
    FORBIDDED(2,"不可借阅");

    private int code;
    private String name;


    public static BookStatus getNameByCode(int code) {
        switch (code){
            case 0 : return DELETED;
            case 1 : return NORMAL;
            case 2 : return FORBIDDED;
        }
        return null;
    }
}
