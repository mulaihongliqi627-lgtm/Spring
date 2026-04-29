package com.amadeus.springbootdemo.controller;

import com.amadeus.springbootdemo.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/request")
public class RequestController {

//    @RequestMapping("/r1")
//    public String r1(int id){//使用基本数据类型int,在传输参数的时候会出现状态码500异常->"error": "Internal Server Error",
//        return String.format("id : %d",id);
//    }

    @RequestMapping("/r1")
    public String r1(Integer id){//使用包装数据类型作为参数,就不会出现5xx异常,在输入参数为空时只会显示为null
        return String.format("id : %d",id);
    }


    @RequestMapping("/r2")
    public String r2(String name){
        return String.format("name : %s",name);

    }

    @RequestMapping("/r3")
    public String r3(Integer id, String name){
        return String.format("id : %d , name : %s",id,name);
    }

    @RequestMapping("/r4")
    public String r4(Integer id,String name,String class_id){
        return String.format("id : %d , name : %s , class : %s",id,name,class_id);
    }

    @RequestMapping("/r5")
    public String r5(Person person){
        return String.format("接收到参数,person : %s",person);
    }

    @RequestMapping("/r6")
    public String r6(@RequestParam(value = "wd", required = false) String keyword){
        return String.format("接收到参数, keyword : %s", keyword);
    }


//    返回一个json对象
    @RequestMapping("/r7")
    public String r7(@RequestBody Person person){
        return "接收到json数据 :"  + person.toString();
    }

    //使用路径传递参数
    @RequestMapping("/r8/{id}/{user_name}")
    public String r8(@PathVariable Integer id, @PathVariable String user_name){
        return String.format("id = %s,user_name = %s",id,user_name);
    }

    @RequestMapping("r9")
    public String getFile(@RequestPart("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return "上传失败,请选择文件!";
        }
        String fileName = file.getOriginalFilename();
        //把上传的文件存在对应的目录下
        file.transferTo(new File("D:/Temp/" + fileName));
        return  "接收到文件名称为" + fileName;
    }

    //返回静态页面
    @RequestMapping("/getIndex")
    public Object getIndex(){
        return "/index.html";
    }





}
