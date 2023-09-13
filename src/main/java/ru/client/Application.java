package ru.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@Slf4j
@ComponentScans(value = {@ComponentScan("ru.client.restRequest"),
        @ComponentScan("ru.client.configs") })
public class Application implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        /*ru.client.restRequest.Client client = ctx.getBean("clnt", ru.client.restRequest.Client.class);
        client.getIngredientList();*/
    }
}