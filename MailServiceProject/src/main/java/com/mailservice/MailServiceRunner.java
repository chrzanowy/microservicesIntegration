package com.mailservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

/**
 * Created by Kuba on 2017-05-18.
 */
@SpringBootApplication(scanBasePackages = "com.mailservice.*")
@Configuration
@EnableJms
@Repository
@Slf4j
public class MailServiceRunner extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MailServiceRunner.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MailServiceRunner.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Bean
    ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory targetConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        targetConnectionFactory.setTrustAllPackages(true);
        return new CachingConnectionFactory(
                targetConnectionFactory);
    }

    @Bean
    JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

}
