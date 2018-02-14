package com.mrkt.wx.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrkt.config.RedisConfig;
import com.mrkt.sys.config.Configurator;
import com.mrkt.usr.core.WxUserAction;
import com.mrkt.wx.core.HttpRequest;
import com.mrkt.wx.core.WxUserServiceImpl;
import com.mrkt.wx.model.WxAccessToken;
import com.mrkt.wx.model.WxUser;

@RestController
public class WxController {

	public final static String token;;
	private final static String  APPID;
	private final static String APPSRECT;
	private final static String GRANT_TYPE;		
	private static final String USER_CACHE = "user";

	protected static Logger logger = LoggerFactory.getLogger(WxController.class);
	
	private HttpRequest httpRequest;
	
	private static Configurator cf;
	
	static{
		cf = Configurator.getInstance();
		cf.load();
		
		GRANT_TYPE = cf.get("wx.grant_type");
		APPSRECT = cf.get("wx.app.srect");
		APPID = cf.get("wx.app.id");
		token = cf.get("wx.token");
	}
	
	@Autowired
	WxUserServiceImpl wxUserServiceImpl;
	
	@Autowired
	@Qualifier("userActionImpl")
	WxUserAction userAction;

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
		StringBuffer temp = new StringBuffer();
		for (String string : arr) {
			temp.append(string);
		}
		return echostr;
	}
	
	@RequestMapping("/wxx/OAuth2/login")
	public Object login(
			@RequestParam(name="code") String code,
			@RequestParam(name="state", required=false) String state
			){
		logger.info("innnnn");
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
