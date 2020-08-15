package net.jisai.pojo;

import java.util.Date;

public class Orders {
	private int id;
	private int userId;
	private String orderNumber;
	private Boolean orderStatus;
	private Double orderAmount;
	private int productId;
	private int productNumber;
	private Date createTime;
	private String paymentMethod;
	private Date payTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Boolean getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Boolean orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(int productNumber) {
		this.productNumber = productNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Orders(int id, int userId, String orderNumber, Boolean orderStatus, Double orderAmount, int productId,
			int productNumber, Date createTime, String paymentMethod, Date payTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.orderAmount = orderAmount;
		this.productId = productId;
		this.productNumber = productNumber;
		this.createTime = createTime;
		this.paymentMethod = paymentMethod;
		this.payTime = payTime;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", userId=" + userId + ", orderNumber=" + orderNumber + ", orderStatus="
				+ orderStatus + ", orderAmount=" + orderAmount + ", productId=" + productId + ", productNumber="
				+ productNumber + ", createTime=" + createTime + ", paymentMethod=" + paymentMethod + ", payTime="
				+ payTime + "]";
	}
	
}
