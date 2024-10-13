package com.morelli.carparts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class  MorelliApplication {
    public static void main(String[] args) {
        SpringApplication.run(MorelliApplication.class, args);
    }
}
