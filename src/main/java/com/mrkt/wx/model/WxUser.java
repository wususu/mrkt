package com.mrkt.wx.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mrkt.usr.model.UserBase;
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) //for test
@Table(name="wx_usr")
@Entity
public class WxUser implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String openID;
	
	private String nickName;
	
	private Boolean sex;
	
	private String province;
	
	private String city;
	
	private String country;
	
	private String headImgUrl;
	
	private String privilege;
	
	private String unionid;
	
	@OneToOne
	@JoinColumn(name="uid", unique=true, nullable=false)
	private UserBase mrktUser;

	public WxUser() {
	}
	
	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public UserBase getMrktUser() {
		return mrktUser;
	}

	public void setMrktUser(UserBase user) {
		this.mrktUser = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((openID == null) ? 0 : openID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WxUser other = (WxUser) obj;
		if (openID == null) {
			if (other.openID != null)
				return false;
		} else if (!openID.equals(other.openID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WxUser [openID=" + openID + ", nickName=" + nickName + ", sex=" + sex + ", province=" + province
				+ ", city=" + city + ", country=" + country + ", headImgUrl=" + headImgUrl + ", privilege=" + privilege
				+ ", unionid=" + unionid + "]";
	}
	
}
