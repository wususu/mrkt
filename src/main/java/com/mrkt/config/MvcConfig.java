package com.mrkt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mrkt.authorization.interceptor.AuthorizationInterceptor;

import javassist.expr.NewArray;

@Configuration
@Component
public class MvcConfig extends WebMvcConfigurerAdapter{

	@Autowired
	private AuthorizationInterceptor authorizationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new AuthorizationInterceptor());
	}
	
}
