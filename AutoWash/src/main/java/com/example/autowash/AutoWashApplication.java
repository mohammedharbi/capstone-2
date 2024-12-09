package com.example.autowash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AutoWashApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoWashApplication.class, args);
    }

}
