package com.example.excel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class ExcelApplication {

    @Value("${project.tmp.files.path}")
    public  String filePath;

    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class, args);
    }

    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // set up file path
        factory.setLocation(filePath);
        return factory.createMultipartConfig();
    }

}
