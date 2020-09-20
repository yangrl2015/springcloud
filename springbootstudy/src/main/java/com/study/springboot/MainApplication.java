package com.study.springboot;

import com.study.springboot.bean.ConfigBean;
import com.study.springboot.configuration.MyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        //SpringApplication.run(MainApplication.class,args);

        AnnotationConfigServletWebApplicationContext context = new AnnotationConfigServletWebApplicationContext();

        context.register(MyConfiguration.class);
        context.refresh();

        ConfigBean configBean = (ConfigBean) context.getBean("configBean");
        System.out.println(configBean);
        System.out.println(context.getBean(MyConfiguration.class));



    }
}
