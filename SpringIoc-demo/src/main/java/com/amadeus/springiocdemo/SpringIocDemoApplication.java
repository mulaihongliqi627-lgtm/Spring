package com.amadeus.springiocdemo;

import com.amadeus.springiocdemo.controller.UserController;
import com.amadeus.springiocdemo.controller.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringIocDemoApplication {
    public static void main(String[] args) {
        //获取Spring上下⽂对象
        ApplicationContext context =
                SpringApplication.run(SpringIocDemoApplication.class, args);
//        //从Spring上下⽂中获取对象
//        UserController userController = context.getBean(UserController.class);
//        //使⽤对象
//        userController.sayHello();

//        //通过类名获取Bean对象
//        UserService userService = context.getBean(UserService.class);

        //通过名称获取Bean对象
        UserService userService = (UserService) context.getBean("userService");
        userService.sayHello();

        UserController userController = context.getBean(UserController.class);
        userController.sayHello();

    }
}
