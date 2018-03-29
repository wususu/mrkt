package com.mrkt.usr.core;

import org.springframework.stereotype.Service;

import com.mrkt.usr.model.Customer;

/**
 * 基础的用户注册，登录和登出操作
 * @author janke
 *
 */
@Service
public interface BaseUserAction {

	public boolean register(Customer user);
	
	public boolean login(Customer user);
	
	public boolean logout(Customer user);
}
