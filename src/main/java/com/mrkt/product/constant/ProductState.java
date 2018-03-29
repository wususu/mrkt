package com.mrkt.product.constant;

/**
 * @ClassName	ProductState
 * @Description 商品状态常量枚举类<br>
 * 				商品状态，0下架；1售卖中；2被预定；3已售出； 
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/03/29 14:03:35
 */
public enum ProductState {

	BE_OFF(0, "已下架"),
	ON_SALE(1, "售卖中"),
	BE_ORDERED(2, "被预定"),
	BE_SOLD(3, "已售出");
	
	private int state;
	private String desc;
	
	ProductState(int state, String desc) {
		this.state = state;
		this.desc = desc;
	}

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
	
}
