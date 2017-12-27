package com.mrkt.usr;

import com.mrkt.usr.model.*;

public class ThisUser {

	protected final static ThreadLocal<UserBase> instance = new ThreadLocal<UserBase>(){
		protected UserBase initialValue() {
			return UnLgnUser.getInstance();
		};
	};
	
	public static UserBase get(){
		return instance.get();
	}
	
	public static void set(UserBase userBase){
		instance.set(userBase);
	}
	
	public static void remove(){
		instance.remove();
	}
}
