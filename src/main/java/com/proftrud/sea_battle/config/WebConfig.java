package com.proftrud.sea_battle.config;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        Integer[] arr = new Integer[]{1,2,3};
        System.out.println(Arrays.toString(arr));
        registry
                .addMapping("/*/**")
                .allowedMethods(CorsConfiguration.ALL)
                .allowedOrigins(
                        "http://localhost",
                        "http://localhost:11001",
                        "http://localhost:3010",
                        "http://localhost:3006",
                        "http://localhost:3006",
                        "http://localhost:3007",
                        "http://localhost:5174",
                        "http://localhost:5175",
                        "http://localhost:5173",
                        "https://178.250.246.116",
                        "http://178.250.246.116",
                        "http://proftrud.ru",
                        "http://catalogmini.com",
                        "http://cm4.pw",
                        "https://cm4.pw",
                        "https://catalogmini.com",
                        "https://ru.catalogmini.com",
                        "https://es.catalogmini.com",
                        "https://proftrud.ru"
                )
                .allowCredentials(true);
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public Random random(){
        return new Random();
    }
}
