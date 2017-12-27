package com.mrkt.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrkt.authorization.annotation.Authorization;

@RestController
public class HomeController {

	@RequestMapping("/")
	@Authorization
	public Object index(){
		return "Hello World";
	}
}
