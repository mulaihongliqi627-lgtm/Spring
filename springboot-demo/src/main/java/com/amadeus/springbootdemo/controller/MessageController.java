package com.amadeus.springbootdemo.controller;


import com.amadeus.springbootdemo.model.MessageInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/message")
@RestController
public class MessageController {
    List<MessageInfo> messageInfoList = new ArrayList<>();

    @GetMapping("/getList")
    public List<MessageInfo> getInfoList(){
        return  messageInfoList;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody MessageInfo messageInfo){
        //messageInfo任意信息不全.返回OK : 0
        if(!StringUtils.hasText(messageInfo.getFrom())
                || !StringUtils.hasText(messageInfo.getTo())
                || !StringUtils.hasText(messageInfo.getMessage())){
            return "{/ok/ : 0}";
        }
        messageInfoList.add(messageInfo);//添加留言信息
        return  "{/ok/ : 1}";
    }
}
