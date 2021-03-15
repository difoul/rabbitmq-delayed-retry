package org.difoul.rabbitmq.delayed.retry.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages = "org.difoul.rabbitmq.delayed.retry")
@SpringBootApplication
public class MySpringBootApp {


	public static void main(String[] args) {
		SpringApplication.run(MySpringBootApp.class, args);
	}

}

