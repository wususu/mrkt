package com.mrkt.authorization.interceptor;

import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.servlet.http.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mrkt.authorization.annotation.Authorization;
import com.mrkt.authorization.core.TokenManager;
import com.mrkt.authorization.model.Token;
import com.mrkt.sys.config.Configurator;
import com.mrkt.usr.ThisUser;
import com.mrkt.usr.core.UserServiceImpl;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter{

	private Configurator cgr;
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	private Logger logger;
	
	private String xAuthType;
	private String xAuthHeader;
	private String xAuthTypeToken;
	private String wxRedirectUri;
	private String wxAppId;
	
	private final static String X_Auth_Type = "com.mrkt.request.auth_type";
	private final static String X_Auth_Header = "com.mrkt.request.auth_header";
	private final static String X_Auth_Type_Token = "com.mrkt.request.auth_type.token";
	
	private final static String WX_APP_ID = "wx.app.id";
	//在线上环境时Redirect_uri应该为前端应用地址
	private final static String WX_APP_REDIRECT_URI = "wx.app.redirect_uri";
	
	
	{
		cgr = Configurator.getInstance();
		logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
		
		this.xAuthType = cgr.get(X_Auth_Type);
		this.xAuthHeader = cgr.get(X_Auth_Header);
		this.xAuthTypeToken = cgr.get(X_Auth_Type_Token);
		this.wxAppId = cgr.get(WX_APP_ID);
		this.wxRedirectUri = cgr.get(WX_APP_REDIRECT_URI);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
        if (!handler.getClass().equals(HandlerMethod.class)) {
			return true;
        }
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		String authType = null;
		String srectStr = null;
		if ( 
				( authType=request.getHeader(xAuthType) )!= null
				&& 
				authType.equals(xAuthTypeToken)
				){
					if ( (srectStr=request.getHeader(xAuthHeader) ) != null) {
						logger.debug("Srect: " + srectStr);
					}
				}
		
		if (method.getAnnotation(Authorization.class) != null){
				if (srectStr == null ) {
					logger.debug("Not Auth");
		        		response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="
		        				+this.wxAppId+
		        				"&redirect_uri="
		        				+	URLEncoder.encode(this.wxRedirectUri)+
		        				"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		        	return false;
				}
		}
		if (srectStr != null) {
			Token token = null;
			token = tokenManager.get(srectStr);
			if (tokenManager.check(srectStr)){
				ThisUser.set(userServiceImpl.get(token.getUid()));
			}
		}

		return true;
	}
}
