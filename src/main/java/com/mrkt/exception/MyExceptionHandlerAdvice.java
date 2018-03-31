package com.mrkt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.mrkt.dto.ReturnModel;

/**
 * @ClassName	MyExceptionHandlerAdvice
 * @Description 全局异常处理
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/08 23:01:44
 */
@ControllerAdvice
public class MyExceptionHandlerAdvice {
	
	private Logger logger = LoggerFactory.getLogger(MyExceptionHandlerAdvice.class);
	
	// 定义全局异常处理，value属性可以过滤拦截条件，此处拦截所有的Exception
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody ReturnModel exception(Exception e, WebRequest request) {
    	logger.error(e.getMessage());
        return ReturnModel.ERROR(ExceptionStatus.ERROR);
    }
}
