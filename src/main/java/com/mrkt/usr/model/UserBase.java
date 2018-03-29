package com.mrkt.usr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mrkt.wx.model.WxUser;

/**
 * 基础用户类型
 * @author janke
 *
 */
@Entity
@Table(name="mrkt_usr")
@DiscriminatorColumn(
	    name="class",
	    discriminatorType=DiscriminatorType.STRING,
	    length=24
	)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class UserBase implements Serializable{

	private static final long serialVersionUID = 6988631888276546963L;

	// FILEDS.INERTIANCE
    /** clazz :string(24) , 多态类型识别.
     * Option values can be found in concrete class's javadoc.
     */
    @Column(name="class", insertable=false, updatable=false)
    protected String clazz;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long uid;
	
	//默认为openid
	@Column(name="usr_name", nullable=true, unique=true)
	private String uName;
	
	//显示的昵称，默认为微信昵称
	@Column(name="usr_nk_name", nullable=true, unique=true)
	private String nName;
	
	@Column(name="usr_avatar")
	private String avatar;
	
	@Column(name="usr_gender")
	private Boolean gender;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="usr_tm_created")
	private Date tmCrtd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="usr_tm_last_login")
	private Date tmLstLgn;
	
	@Column(name="usr_isdelete")
	private Boolean isDelete;
	
	// 以下为校园身份信息
	@Column(name="usr_stu_number")
	private String stuNmbr;

	public UserBase() {
	}
	
	public UserBase(WxUser wxUser){
		this(wxUser.getOpenID(), wxUser.getNickName(), wxUser.getHeadImgUrl(), wxUser.getSex(), 
				new Date(/*NOW*/), new Date(/*NOW*/), false, null);
	}
	
	public UserBase(String uName, String nName, String avatar, Boolean gender, Date tmCrtd, Date tmLstLgn, Boolean isDelete,
			String stuNmbr) {
		super();
		this.uName = uName;
		this.nName = nName;
		this.avatar = avatar;
		this.gender = gender;
		this.tmCrtd = tmCrtd;
		this.tmLstLgn = tmLstLgn;
		this.isDelete = isDelete;
		this.stuNmbr = stuNmbr;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getnName() {
		return nName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public Date getTmCrtd() {
		return tmCrtd;
	}

	public void setTmCrtd(Date tmCrtd) {
		this.tmCrtd = tmCrtd;
	}

	public Date getTmLstLgn() {
		return tmLstLgn;
	}

	public void setTmLstLgn(Date tmLstLgn) {
		this.tmLstLgn = tmLstLgn;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getStuNmbr() {
		return stuNmbr;
	}

	public void setStuNmbr(String stuNmbr) {
		this.stuNmbr = stuNmbr;
	}
    
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return "UserBase [clazz=" + clazz + ", uid=" + uid + ", uName=" + uName + ", nName=" + nName + ", avatar="
				+ avatar + ", gender=" + gender + ", tmCrtd=" + tmCrtd + ", tmLstLgn=" + tmLstLgn + ", isDelete="
				+ isDelete + ", stuNmbr=" + stuNmbr + "]";
	}
	
	
}
