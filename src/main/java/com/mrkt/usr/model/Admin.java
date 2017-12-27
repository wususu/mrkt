package com.mrkt.usr.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.mrkt.wx.model.WxUser;

/**
 * 管理员
 * @author janke
 *
 */
@Entity
@DiscriminatorValue(".admn")
public class Admin extends UserBase
implements Serializable{

	private static final long serialVersionUID = -3177295695776605542L;
	
	public Admin() {
		// TODO Auto-generated constructor stub
		super();
		return ;
	}
	
	public Admin(WxUser wxUser){
		super(wxUser);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this) {
			return true;
		}
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}
		Admin a = (Admin)obj;
		if (
				a.getUid() == this.getUid()
				||
				(a.getUid() != null && a.getuName() != null)
				&&
				(a.getUid().equals(this.getUid()) && a.getuName().equals(this.getuName())) 
				) {
			return true;
		}
		return false;
	}
}
