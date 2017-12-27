package com.mrkt.usr.core;

import org.springframework.stereotype.Service;

import com.mrkt.wx.model.WxUser;

@Service
public interface WxUserAction{

	// 通过微信号注册
	public boolean register(WxUser wxUser);
	
	// 微信登录
	public boolean login(WxUser wxUser);
	
	public boolean logout(WxUser wxUser);
}
