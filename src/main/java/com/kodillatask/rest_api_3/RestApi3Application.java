package com.kodillatask.rest_api_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestApi3Application /* extends SpringBootServletInitializer*/{

    public static void main(String[] args) {
        SpringApplication.run(RestApi3Application.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//            return application.sources(RestApi3Application.class);
//    }

}
