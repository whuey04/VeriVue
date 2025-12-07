package com.verivue.comment.config;
 
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@ComponentScan(basePackages = {
        "com.verivue.api.article.fallback"
})
public class InitConfig {

}