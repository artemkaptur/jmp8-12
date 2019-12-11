package com.epam.springboot.task1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.epam.springboot.task1")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
