package com.amadeus.springbootdemo.model;
import  lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfo {
    private String from;
    private String to;
    private String message;

}