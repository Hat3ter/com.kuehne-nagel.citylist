package com.solbegsoft.citylist;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CitylistApplication {

    @SneakyThrows
    public static void main(String[] args) {

        SpringApplication.run(CitylistApplication.class, args);
    }
}
