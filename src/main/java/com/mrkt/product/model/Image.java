package com.mrkt.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName	Image
 * @Description 用于映射商品的图片实体类
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/21 17:02:37
 */
@Entity
@Table(name="mrkt_pro_image")
public class Image implements Serializable {
	private static final long serialVersionUID = -3541433090922462549L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="image_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	@JSONField(serialize=false)
	private Product product;    // 多对一关联商品
	
	@Column(name="image_path")
	private String path;
	
	public Image() {}
	public Image(String path) {this.path = path;}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
