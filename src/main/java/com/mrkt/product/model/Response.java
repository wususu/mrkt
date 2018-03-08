package com.mrkt.product.model;

/**
 * @ClassName	Response
 * @Description 响应 值对象
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/08 21:38:20
 */
public class Response {

	private Boolean success;
	private String message;
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Response() {
		super();
	}
	public Response(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
}
