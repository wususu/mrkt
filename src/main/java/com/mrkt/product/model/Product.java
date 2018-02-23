package com.mrkt.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.mrkt.usr.model.UserBase;

/**
 * 商品实体类
 * @author hdonghone
 */
@Entity
@Table(name="mrkt_product")
public class Product implements Serializable{

	private static final long serialVersionUID = 6988631888276546963L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_id")
	private Long id;
	
	@Column(name="product_name")
	private String name;          // 商品名
	
	@Column(name="product_desc")
	private String desc;          // 商品描述
	
	@Column(name="product_price")
	private Double price;         // 价格
	
//	@Column(name="product_image") // 改用表映射多张图片
//	private String image;         // 图片路径
	
	@Column(name="product_views")
	private Integer views = 0;        // 浏览量
	
	@Column(name="product_collection")
	private Integer collection = 0;   // 收藏数
	
	@Column(name="product_likes")
	private Integer likes = 0;        // 点赞数
	
	@Column(name="product_type")
	private String ptype;         // 商品类型
	
	@Column(name="product_tra_way")
	private String traWay;        // 卖家支持的交易方式，第一版默认只支持当面交易
	
	@Column(name="product_count")
	private Integer count;        // 商品库存/余量，因为二手市场，全默认为1
	
	@Column(name="product_state")
	private Integer state;        // 商品状态，0下架/删除；1发布；2已售出；
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="product_tm_created")
	private Date tmCreated;       // 记录创建时间/商品发布时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="product_tm_updated")
	private Date tmUpdated;       // 记录更新时间/商品修改时间
	
	@ManyToOne
	@JoinColumn(name="uid")
	private UserBase mrktUser;    // 多对一关联用户表
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="product_id")
	private Set<Image> images = new HashSet<>(); // 一对多关联图片
	
	/**
	 * 冗余字段，标志当前用户是否对该商品赞过，不存入数据库
	 */
	@Transient
	private Boolean isLike;

	public Product() {
		super();
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", desc=" + desc + ", price=" + price + ", views=" + views
				+ ", collection=" + collection + ", likes=" + likes + ", ptype=" + ptype + ", traWay=" + traWay
				+ ", count=" + count + ", state=" + state + ", tmCreated=" + tmCreated + ", tmUpdated=" + tmUpdated
				+ ", mrktUser=" + mrktUser + ", images=" + images + ", isLike=" + isLike + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

//	public String getImage() {
//		return image;
//	}
//
//	public void setImage(String image) {
//		this.image = image;
//	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getCollection() {
		return collection;
	}

	public void setCollection(Integer collection) {
		this.collection = collection;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getTraWay() {
		return traWay;
	}

	public void setTraWay(String traWay) {
		this.traWay = traWay;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getTmCreated() {
		return tmCreated;
	}

	public void setTmCreated(Date tmCreated) {
		this.tmCreated = tmCreated;
	}

	public Date getTmUpdated() {
		return tmUpdated;
	}

	public void setTmUpdated(Date tmUpdated) {
		this.tmUpdated = tmUpdated;
	}

	public UserBase getMrktUser() {
		return mrktUser;
	}

	public void setMrktUser(UserBase mrktUser) {
		this.mrktUser = mrktUser;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Boolean getIsLike() {
		return isLike;
	}

	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}
	
}
