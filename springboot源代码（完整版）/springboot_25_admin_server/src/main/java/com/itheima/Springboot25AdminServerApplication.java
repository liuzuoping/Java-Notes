package com.itheima;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class Springboot25AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot25AdminServerApplication.class, args);
    }

}
