package com.example.demo.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ProductSellerDTO {
	List<ProductDTO> products;
	
	@NotNull
	long userId;
	
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
