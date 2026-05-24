package com.amadeus.lotterysystem.common.errorcode;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorCode {

    private final Integer code;
    private final String msg;

}
