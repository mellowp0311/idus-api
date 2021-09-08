package com.idus;

import com.idus.properties.IDusConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(basePackages = IDusConstant.BASE_MAPPER_PACKAGE)
public class IdusApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdusApiApplication.class, args);
    }

}
