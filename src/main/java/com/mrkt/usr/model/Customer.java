package com.mrkt.usr.model;

import java.io.Serializable;

import javax.persistence.*;

import com.mrkt.wx.model.WxUser;

/**
 * 普通用户
 * @author janke
 *
 */
@Entity
@DiscriminatorValue(".cust")
public class Customer extends UserBase
implements Serializable{

	private static final long serialVersionUID = -6556977703181333226L;

	public Customer() {
		// TODO Auto-generated constructor stub
		super();
		return ;
	}
	
	public Customer(WxUser wxUser)
	{
		super(wxUser);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this){
			return true;
		}
		if (obj==null || !obj.getClass().equals(this.getClass())) {
			return false;
		}
		Customer c = (Customer)obj;
		if (c.getUid() == this.getUid() 
				||
				(c.getUid() != null && c.getuName() != null)
				&&
				(c.getUid().equals(this.getUid()) && c.getuName().equals(this.getuName()))
				) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format(
				"%d#%s%s",
				this.getUid(), this.getuName(), this.getnName());
	}
}
