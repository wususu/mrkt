package com.mrkt.product.constant;

/**
 * @ClassName	OrderState
 * @Description 订单状态常量枚举类<br>
 * 				0取消，1请求预定等待卖家接受预定，2待支付，3待确定收货，4待双方评价，5交易完成， -1已删除
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/29 14:25:32
 */
public enum OrderState {

	BE_DELETED(-1, "订单已被删除"),
	BE_CANCELED(0, "订单已取消"),
	BE_WAITING_ACCEPTED(1, "等待接受预定"),
	BE_WAITING_PYAMENT(2, "待支付，须完善收货人信息"),
	BE_WAITING_RECEIVING(3, "待确定收货"),
	BE_COMMENTING(4, "待双方互评"),
	BE_COMPLETE(5, "交易完成");
	
	private int state;
	private String desc;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private OrderState(int state, String desc) {
		this.state = state;
		this.desc = desc;
	}
	
}
