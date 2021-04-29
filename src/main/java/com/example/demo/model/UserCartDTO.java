package com.example.demo.model;

import java.util.List;

public class UserCartDTO {
	private long userId;
	private List<Long> productId;
	private List<Short> quantity;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public List<Long> getProductId() {
		return productId;
	}
	public void setProductId(List<Long> productId) {
		this.productId = productId;
	}
	public List<Short> getQuantity() {
		return quantity;
	}
	public void setQuantity(List<Short> quantity) {
		this.quantity = quantity;
	}
}
