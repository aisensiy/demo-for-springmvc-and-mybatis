package com.example.api;

import com.example.api.exception.CustomizeExceptionHandler;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class ApiTest {
    protected HandlerExceptionResolver createExceptionResolver() {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerSingleton("exceptionHandler", CustomizeExceptionHandler.class);

        WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        return webMvcConfigurationSupport.handlerExceptionResolver();
    }
}
