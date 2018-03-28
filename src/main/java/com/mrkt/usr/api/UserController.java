package com.mrkt.usr.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.dto.ReturnModel;
import com.mrkt.usr.core.UserServiceImpl;

/**
 * @ClassName	UserController
 * @Description 顾客的控制层
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/06 22:26:22
 */
@RestController
@RequestMapping("/customer")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	/**
	 * 查看顾客资料
	 * @param uid
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{uid}", method=RequestMethod.GET)
	public ReturnModel getUser(@PathVariable("uid") Long uid) {
		return ReturnModel.SUCCESS(userService.get(uid));
	}
	
	/**
	 * 修改顾客信息
	 * @param uid
	 * @return
	 */
	@Authorization
	@RequestMapping(value="/{uid}", method=RequestMethod.PUT)
	public String editUser(@PathVariable("uid") Long uid) {
		// 修改
		
		return JSON.toJSONString(userService.get(uid));
	}
	
}
