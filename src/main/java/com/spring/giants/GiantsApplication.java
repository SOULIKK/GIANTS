package com.spring.giants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GiantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiantsApplication.class, args);
    }

}
