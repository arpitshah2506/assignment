package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.UserCart;

public interface UserCartCustom {
	boolean findCartForUser(long userId, long productId, short quantity);
	boolean isUserCartExist(long userId);
	List<UserCart> getProductsInCart(long userId);
}
