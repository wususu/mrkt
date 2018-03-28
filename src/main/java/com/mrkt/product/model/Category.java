package com.mrkt.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 商品类型 实体
 */
@Entity // 实体
@Table(name="mrkt_pro_cat")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	@Column(name = "cat_id")
	private Long id;          // 商品类型的唯一标识

	@Column(nullable = false) // 映射为字段，值不能为空
	private String name;      // 类型名称
	
	@Column(name = "create_time")
	@CreationTimestamp        // 由数据库自动创建时间
	private Timestamp createTime;
 
	@Column(name = "update_time")
	@UpdateTimestamp
	private Timestamp updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
	
}
