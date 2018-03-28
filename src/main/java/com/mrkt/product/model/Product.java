package com.mrkt.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private Double price=0d;         // 价格
	
	@Column(name="product_views")
	private Integer views = 0;        // 浏览量
	
	@Column(name="product_collection")
	private Integer collection = 0;   // 收藏数
	
	@Column(name="product_likes")
	private Integer likes = 0;        // 点赞数
	
	@Column(name="product_type")
	private String ptype;         // 商品分类名称，冗余字段
	
	@Column(name="product_tra_way")
	private String traWay;        // 卖家支持的交易方式，第一版默认只支持当面交易
	
	@Column(name="product_count")
	private Integer count = 1;        // 商品库存/余量，因为二手市场，全默认为1
	
	@Column(name="product_state")
	private Integer state;        // 商品状态，0下架/删除；1发布；2被预定；3已售出；
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="product_tm_created")
	private Date tmCreated;       // 记录创建时间/商品发布时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="product_tm_updated")
	private Date tmUpdated;       // 记录更新时间/商品修改时间
	
	@Column(name="cat_id")
	private Long catId;           // 商品分类id
	
	@ManyToOne
	@JoinColumn(name="uid")
	private UserBase mrktUser;    // 多对一关联用户表
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Set<Image> images = new HashSet<>(); // 一对多关联图片
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Set<Comment> comments = new HashSet<>(); // 一对多关联评论
	
	/**
	 * 冗余字段，标志当前用户是否对该商品赞过，不存入数据库
	 */
	@Transient
	private Boolean isLike = false;
	/**
	 * 冗余字段，标志当前用户是否收藏过该商品，不存入数据库
	 */
	@Transient
	private Boolean isColl = false;

	public Product() {
		super();
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", desc=" + desc + ", price=" + price + ", views=" + views
				+ ", collection=" + collection + ", likes=" + likes + ", ptype=" + ptype + ", traWay=" + traWay
				+ ", count=" + count + ", state=" + state + ", tmCreated=" + tmCreated + ", tmUpdated=" + tmUpdated
				+ ", catId=" + catId + ", mrktUser=" + mrktUser + ", images=" + images + ", comments=" + comments
				+ ", isLike=" + isLike + ", isColl=" + isColl + "]";
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

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public Boolean getIsColl() {
		return isColl;
	}

	public void setIsColl(Boolean isColl) {
		this.isColl = isColl;
	}
	
	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	/**
	 * 添加评论
	 * @param comment
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
//		this.commentSize = this.comments.size();
	}
	
	/**
	 * 删除评论
	 * @param comment
	 */
	public void removeComment(Long commentId) {
		for (Comment comment : comments)
			if (comment.getId() == commentId) {
				this.comments.remove(comment);
				break;
			}
		
//		this.commentSize = this.comments.size();
	}
}
