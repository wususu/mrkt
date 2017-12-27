package com.mrkt.usr.model;

import java.io.Serializable;

/**
 * 未登录用户
 * @author janke
 *
 */
public class UnLgnUser extends UserBase
implements Serializable{

	private static final long serialVersionUID = 864845526891101891L;
	
	private final static String CLASS_DISCRIMINTOR = "u.unlg";

	private static class Inner{
		public static UnLgnUser instance = new UnLgnUser();
	}
	
	public static UnLgnUser getInstance(){
		return Inner.instance;
	}
	
	@Override
	public String getClazz() {
		// TODO Auto-generated method stub
		return CLASS_DISCRIMINTOR;
	}
	
	@Override
	public void setClazz(String clazz) {
		// TODO Auto-generated method stub
		return ;
	}
	
	private UnLgnUser() {
		// TODO Auto-generated constructor stub
		super();
		super.setClazz(CLASS_DISCRIMINTOR);
		return ;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass().equals(this.getClass())) {
			return false;
		}
		UnLgnUser u = (UnLgnUser)obj;
        return(
                (
                    true
                )
                &&
                (
                    (this.getUid()== u.getUid()) ||
                    (this.getUid() != null && this.getUid().equals(u.getUid()))
                )
            );
	}
}
