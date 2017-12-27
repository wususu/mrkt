package com.mrkt;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={
		"com.mrkt",
		"com.mrkt.config",
		"com.mrkt.authorization",
		"com.mrkt.usr",
		"com.mrkt.wx",
})
@EnableJpaRepositories(basePackages = {
		"com.mrkt.wx.dao",
		"com.mrkt.usr.dao",
		})
@EntityScan(basePackages={
		"com.mrkt.wx.model",
		"com.mrkt.usr.model"
})
public class MrkttApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrkttApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx){
//		return args -> {
//			System.out.println("Let's inspect the beans provided by SpringBoot:");
//			
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for(String beanName: beanNames){
//				System.out.println(beanName);;
//			}
//		};
//	}
}
