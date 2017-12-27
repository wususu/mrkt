package com.mrkt.usr.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mrkt.usr.dao.UserRepository;
import com.mrkt.usr.model.UserBase;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redisTemplates;
	
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	public UserBase get(Long uid){
		UserBase user = null;
		if (( user = (UserBase) redisTemplates.opsForHash().get("user", uid) ) != null){
			//nothing
			logger.debug("Cache hit, user: " + user);
		}else{
			if ( (user = userRepository.findOne(uid)) != null ) {
				logger.debug("Get from DB, user: " + user);
				redisTemplates.opsForHash().put("user", user.getUid(), user);
			}
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public UserBase save(UserBase user){
		user = userRepository.saveAndFlush(user);
		redisTemplates.opsForHash().put("user", user.getUid(), user);
		return user;
	}
}
