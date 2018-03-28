package com.mrkt.usr.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.mrkt.usr.dao.UserRepository;
import com.mrkt.usr.model.Customer;
import com.mrkt.usr.model.UserBase;
import com.mrkt.wx.core.WxUserServiceImpl;
import com.mrkt.wx.dao.WxUserRepository;
import com.mrkt.wx.model.WxUser;

public class AbstractUserAction implements BaseUserAction, WxUserAction{

	private Logger logger = LoggerFactory.getLogger(AbstractUserAction.class);
	
	@Autowired
	private WxUserServiceImpl wxUserServiceImpl;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean register(WxUser wxUser) {
		if (wxUser.getMrktUser() != null) {
			return true;
		}
		UserBase user = null;
		if (wxUser.getOpenID() != null && wxUser.getNickName() != null) {
			user = new Customer(wxUser);
			save(user, wxUser);
			logger.info("register: " + " user: " + user +", wxUser: " + wxUser);
			return true;
		}
		return false;
	}
	
	@Transactional
	private void save(UserBase user, WxUser wxUser){
		user = userServiceImpl.save(user);
		wxUser.setMrktUser(user);
		wxUserServiceImpl.save(wxUser);
	}

	@Override
	public boolean login(WxUser wxUser) {
		UserBase user = null, fUser  = null;
		Long uid = null;
		
		if ((user = wxUser.getMrktUser()) != null && (uid=user.getUid()) != null) {
			fUser = userServiceImpl.get(uid);
			if (fUser != null) {
				logger.info("Login: success");
			}
			return fUser == null ? false : true;
		}
		logger.info("Login: fail");
		return false;
	}

	@Override
	public boolean logout(WxUser wxUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(Customer user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(Customer user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logout(Customer user) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
