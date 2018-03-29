package com.mrkt.authorization.core;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mrkt.authorization.model.Token;
import com.mrkt.sys.config.Configurator;
import com.mrkt.usr.model.UserBase;

@Service
public class RedisTokenManager implements TokenManager{

	private Configurator cgr;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	private Long tmTokenExpire;
	private Long tmTokenIncrease;
	
	private final static String Token_Expire_Time = "com.mrkt.token.expire_time";
	private final static String Token_Increase_Time = "com.mrkt.token.increase_time";
	
	{
		cgr = Configurator.getInstance();
		
		tmTokenExpire  = cgr.getLong(Token_Expire_Time);
		tmTokenIncrease = cgr.getLong(Token_Increase_Time);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Token create(UserBase userBase) {
		Token token = null;
		if( (token=TokenIssues.tokenGenerator(userBase)) != null ){
			redisTemplate.boundValueOps(token.getSrect()).set(token, tmTokenExpire, TimeUnit.MINUTES);
		}
		return token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean check(String srect) {
		Token token = null;
		if( 
				srect == null || srect.length() != 32
				||
				(token = get(srect) ) == null
				){
			return false;
		}
		redisTemplate.boundValueOps(token.getSrect()).expire(tmTokenIncrease, TimeUnit.MINUTES);
		return true;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Token get(String srect) {
		Token token = null;
		if(
				(token = (Token) redisTemplate.boundValueOps(srect).get() ) != null
				){
					// nothing
				}
		return token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(String srect) {
		redisTemplate.boundValueOps(srect).expireAt(new Date());
	}

}
