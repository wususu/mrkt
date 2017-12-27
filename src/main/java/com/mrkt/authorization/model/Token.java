package com.mrkt.authorization.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Token implements Serializable{

	private static final long serialVersionUID = 7551584677094196618L;

	private String srect;
	
	@JsonIgnoreProperties
	private String openID;
	
	private Long uid;
	
	public Token(String srect, String openID, Long uid) {
		super();
		this.srect = srect;
		this.openID = openID;
		this.uid = uid;
	}

	public Token() {
		// TODO Auto-generated constructor stub
	}
	
	public String getSrect() {
		return srect;
	}

	public void setSrect(String srect) {
		this.srect = srect;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Token [srect=" + srect + ", openID=" + openID + ", uid=" + uid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((openID == null) ? 0 : openID.hashCode());
		result = prime * result + ((srect == null) ? 0 : srect.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		Token other = (Token) obj;
		if (openID == null) {
			if (other.openID != null)
				return false;
		} else if (!openID.equals(other.openID))
			return false;
		if (srect == null) {
			if (other.srect != null)
				return false;
		} else if (!srect.equals(other.srect))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

}
