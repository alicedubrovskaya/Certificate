package com.epam.esm.config;

import com.epam.esm.repository.configuration.RepositoryConfiguration;
import com.epam.esm.service.configuration.ServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Import({ServiceConfiguration.class, RepositoryConfiguration.class})
@ComponentScan("com.epam.esm.controller")
public class WebConfiguration implements WebMvcConfigurer {
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        return new MethodValidationPostProcessor();
//    }
}
