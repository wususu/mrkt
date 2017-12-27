package com.mrkt.wx.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mrkt.wx.dao.WxUserRepository;
import com.mrkt.wx.model.WxUser;

@Service
public class WxUserServiceImpl {

	@Autowired
	private WxUserRepository wxUserRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("redisTemplate")
	RedisTemplate redisTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(WxUserServiceImpl.class);
	
	/**
	 * 优先缓存取
	 * @param openID wechat openid
	 * @return WxUser or null
	 */
	@SuppressWarnings("unchecked")
	public WxUser get(String openID){
		WxUser wxUser = null;
		if ( (wxUser = (WxUser)redisTemplate.opsForHash().get("wx_user", openID)) != null
				){
			// nothing
			logger.debug("Cache hit, wx_user: " +wxUser);
		}else{
			if ( (wxUser = wxUserRepository.findOne(openID) ) != null){
				logger.debug("Get From DB, wx_user: " +wxUser);
				redisTemplate.opsForHash().put("wx_user", wxUser.getOpenID(), wxUser);
			}
		}
		return wxUser;
	}
	
	@SuppressWarnings("unchecked")
	public WxUser save(WxUser wxUser){
		wxUser = wxUserRepository.saveAndFlush(wxUser);
		redisTemplate.opsForHash().put("wx_user", wxUser.getOpenID(), wxUser);
		return wxUser;
	}
}
