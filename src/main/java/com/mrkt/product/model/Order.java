package com.mrkt.product.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName	Order
 * @Description 订单实体
 * @author		hdonghong
 * @version 	v1.0
 * @since		2018/02/24 20:36:27
 */
@Entity
@Table(name="mrkt_pro_order")
public class Order implements Serializable {

	private static final long serialVersionUID = -2093781998417319923L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")// 声明一个策略通用生成器，name为”system-uuid”,策略strategy为”uuid”
	@GeneratedValue(generator = "system-uuid")// 用generator属性指定要使用的策略生成器。
	@Column(name="order_id")
	private String id;
	
	/**
	 * 订单与商品 一对一关联
	 */
	@OneToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@Column(name = "buyer_id")
	private Long buyerId;                   // 买家id，实际是当前用户id
	
	@Column(name = "buyer_name")
	private String buyerName;               // 收方联系人

	@Column(name = "buyer_wx")
	private String buyerWx;                 // 买家微信

	@Column(name = "buyer_phone")
	private String buyerPhone;              // 买家必填

	@Column(name = "buyer_score")
	private Integer buyerScore = 0;         // 对买家评分

	@Column(name = "buyer_comment")
	private String buyerComment;            // 对买家的评语

	@Column(name = "seller_id")
	private Long sellerId;                  // 卖家id，实际为商品所属卖家的uid

	@Column(name = "seller_name")
	private String sellerName;              // 卖家称呼，实际为微信名

	@Column(name = "seller_score")
	private Integer sellerScore = 0;        // 对卖家评分

	@Column(name = "seller_comment")
	private String sellerComment;           // 对卖家的评语

	@Column(name = "amount")
	private Double amount = 0d;             // 总金额

	@Column(name = "state")
	private Integer state = 1;              // 订单状态，0取消，1请求预定等待卖家接受预定，2待支付，3待确定收货，4待双方评价， -1已删除

	@Column(name = "message")
	private String message;                 // 买家预定留言

	@Column(name = "address")               // 地址
	private String address;
	
	@Column(name = "create_time")
	private Date createTime;                // 订单生成时间

	@Column(name = "end_time")
	private Date endTime;                   // 订单完成时间

	public Order() {
		super();
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", product=" + product + ", buyerId=" + buyerId + ", buyerName=" + buyerName
				+ ", buyerWx=" + buyerWx + ", buyerPhone=" + buyerPhone + ", buyerScore=" + buyerScore
				+ ", buyerComment=" + buyerComment + ", sellerId=" + sellerId + ", sellerName=" + sellerName
				+ ", sellerScore=" + sellerScore + ", sellerComment=" + sellerComment + ", amount=" + amount
				+ ", state=" + state + ", message=" + message + ", address=" + address + ", createTime=" + createTime
				+ ", endTime=" + endTime + "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerWx() {
		return buyerWx;
	}

	public void setBuyerWx(String buyerWx) {
		this.buyerWx = buyerWx;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public Integer getBuyerScore() {
		return buyerScore;
	}

	public void setBuyerScore(Integer buyerScore) {
		this.buyerScore = buyerScore;
	}

	public String getBuyerComment() {
		return buyerComment;
	}

	public void setBuyerComment(String buyerComment) {
		this.buyerComment = buyerComment;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Integer getSellerScore() {
		return sellerScore;
	}

	public void setSellerScore(Integer sellerScore) {
		this.sellerScore = sellerScore;
	}

	public String getSellerComment() {
		return sellerComment;
	}

	public void setSellerComment(String sellerComment) {
		this.sellerComment = sellerComment;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
