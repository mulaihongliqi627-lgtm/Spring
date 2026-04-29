package com.amadeus.bookdemo.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200),
    FAIL(-2),
    UNLOGIN(-1),
    ;
    private int code;
}
