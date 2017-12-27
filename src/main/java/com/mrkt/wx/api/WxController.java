package com.mrkt.wx.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.jboss.jandex.TypeTarget.Usage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrkt.config.MvcConfig;
import com.mrkt.config.RedisConfig;
import com.mrkt.usr.core.UserActionImpl;
import com.mrkt.usr.core.WxUserAction;
import com.mrkt.wx.core.HttpRequest;
import com.mrkt.wx.core.WxUserServiceImpl;
import com.mrkt.wx.dao.WxUserRepository;
import com.mrkt.wx.model.WxAccessToken;
import com.mrkt.wx.model.WxUser;

@RestController
public class WxController {

	public final static String token = "818181";
	
	private final static String  APPID = "wx2b64787e18521a4b";
	
	private final static String APPSRECT = "1aa0d1c44223eb93fb3799606e9e470d"; 
	
	private final static String GRANT_TYPE = "authorization_code";
	
	protected Logger logger = LoggerFactory.getLogger(WxController.class);
	
	private HttpRequest httpRequest;
	
	private static final String USER_CACHE = "user";
	
	@Autowired
	WxUserServiceImpl wxUserServiceImpl;
	
	@Autowired
	@Qualifier("userActionImpl")
	WxUserAction userAction;
//	
//	@Autowired
//	MvcConfig mvcConfig;
	@Autowired
	RedisConfig redisConfig;
	
	{
		httpRequest = HttpRequest.getInstance();
	}
	
	@RequestMapping("/wxx")
	public Object test(
			@RequestParam(required=false, name="signature") String signature,
			@RequestParam(required=false, name="timestamp") String timestamp,
			@RequestParam(required=false, name="nonce") String nonce,
			@RequestParam(required=false, name="echostr") String echostr
			){
		String[] arr = new String[]{timestamp, nonce, token};
		Arrays.sort(arr);
		StringBuffer temp = new StringBuffer();
		for (String string : arr) {
			temp.append(string);
		}
		System.out.println(temp);
		return echostr;
	}
	
	@RequestMapping("/wxx/OAuth2/login")
	public Object login(
			@RequestParam(name="code") String code,
			@RequestParam(name="state", required=false) String state
			){
		String response = httpRequest.doGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APPSRECT+"&code="+code+"&grant_type=authorization_code");
		WxUser wxuser = null;
		WxAccessToken accessToken = JSONObject.parseObject(response, WxAccessToken.class);
		if (accessToken.getOpenid()!=null) {
			wxuser = wxUserServiceImpl.get(accessToken.getOpenid());
			if (wxuser!=null &&( userAction.login(wxuser))) {
				return wxuser;
			}
		}
		if (accessToken.getAccess_token() != null && accessToken.getOpenid()!=null) {
			String resp = httpRequest.doGet("https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken.getAccess_token()+"&openid="+accessToken.getOpenid()+"&lang=zh_CN");
			
			wxuser = JSON.parseObject(resp, WxUser.class);
			userAction.register(wxuser);
		}
//		System.out.println(mrktRedisTemplate);
		return (wxuser==null||wxuser.getNickName()==null)? null:wxuser;
	}
}
