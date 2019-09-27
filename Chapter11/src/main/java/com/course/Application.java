package com.course;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@EnableScheduling
@SpringBootApplication
//Application为启动程序
public class Application {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args){
        Application.context = SpringApplication.run(Application.class,args);
    }

    @PreDestroy
    //预摧毁方法
    public void close(){
        Application.context.close();
    }
}
