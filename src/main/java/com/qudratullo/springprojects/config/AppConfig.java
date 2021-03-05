package com.qudratullo.springprojects.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.qudratullo.springprojects")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class AppConfig {
}
