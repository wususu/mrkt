package com.mrkt.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.mrkt.usr.model.UserBase;

/**
 * 商品留言 实体
 */
@Entity // 实体
@Table(name="mrkt_pro_comment")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	@Column(name="comment_id")
	private Long id; // 用户的唯一标识

	@NotEmpty(message = "留言内容不能为空")
	@Size(min=2, max=255)
	@Column(nullable = false) // 映射为字段，值不能为空
	private String content;
 
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="uid")
	private UserBase UserBase;
	
	@Column(nullable = false) // 映射为字段，值不能为空
	@org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
	private Timestamp createTime;
	
	@Transient
	/**
	 * 冗余字段，标志评论是否属于当前用户
	 */
	private Boolean belongCurrUser = false;
 
	protected Comment() {
		// TODO Auto-generated constructor stub
	}
	public Comment(UserBase UserBase, String content) {
		this.content = content;
		this.UserBase = UserBase;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public UserBase getUser() {
		return UserBase;
	}
	public void setUser(UserBase UserBase) {
		this.UserBase = UserBase;
	}
 
	public Timestamp getCreateTime() {
		return createTime;
	}
	public Boolean getBelongCurrUser() {
		return belongCurrUser;
	}
	public void setBelongCurrUser(Boolean belongCurrUser) {
		this.belongCurrUser = belongCurrUser;
	}
	
}
