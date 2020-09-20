package com.study.springboot.configuration;

import com.study.springboot.bean.ConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class MyConfiguration {

    @Bean(name = "configBean")
    public ConfigBean testStr(){

        return  new ConfigBean();
    }

}
