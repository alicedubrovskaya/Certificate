package com.epam.esm.config;

import com.epam.esm.repository.configuration.RepositoryConfiguration;
import com.epam.esm.service.configuration.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import({ServiceConfiguration.class, RepositoryConfiguration.class})
@ComponentScan("com.epam.esm.controller")

public class WebConfiguration {
}
