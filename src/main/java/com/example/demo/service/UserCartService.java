package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;
import com.example.demo.entity.UserCart;
import com.example.demo.entity.UserOrder;
import com.example.demo.model.UserCartDTO;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserCartRepository;
import com.example.demo.repository.UserOrderRepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserCartService {
	@Autowired
	private UserCartRepository userCartRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserOrderRepository userOrderRepository;
	
	public boolean addToCart(UserCartDTO userCart) {
		Map<Long, Short> itemQuantityAssoc = new HashMap<>();
		
		List<Long> productIds = userCart.getProductId();
		List<Short> quantities = userCart.getQuantity();
		
		//remove duplicate product entry from input and combine the quantity if duplicates found
		for (int index = 0; index < productIds.size(); index++) {
			if (itemQuantityAssoc.containsKey(productIds.get(index))) {
				itemQuantityAssoc.put(productIds.get(index), (short) (itemQuantityAssoc.get(productIds.get(index)) + quantities.get(index)));
			} else {
				itemQuantityAssoc.put(productIds.get(index), quantities.get(index));
			}
		}
		
		if (unavailableItemProvided(itemQuantityAssoc) == false)
		{
			for (Map.Entry<Long, Short> userEntry : itemQuantityAssoc.entrySet()) {
				userCartRepository.findCartForUser(userCart.getUserId(), userEntry.getKey(), userEntry.getValue());
			}	
		} else {
			return false;
		}
				
		return true;
	}

	private boolean unavailableItemProvided(Map<Long, Short> itemQuantityAssoc) {
		List<Long> list = new ArrayList<Long>(itemQuantityAssoc.size());
		itemQuantityAssoc.keySet().stream().forEach(x -> list.add(x));
		
		List<Product> products = (List<Product>) productRepository.findAllById(list);
		
		if (products == null || products.size() == 0 || products.size() != itemQuantityAssoc.size()) {
			return true;
		}
		return false;
	}

	public String placeOrder(long userId) {
		if (userCartRepository.isUserCartExist(userId) == false)
		{
			return "No item in the cart";
		}
		
		//mocking payment service
		if (isPaymentSuccessful() == false) {
			return "Sorry, payment failed";
		}
		
		List<UserCart> userCartProduct = userCartRepository.getProductsInCart(userId);
		
		List<Long> productIds = new ArrayList<>();
		userCartProduct.stream().forEach(x -> productIds.add(x.getProductId()));
		
		Map<Long, Float> itemAndPrice = new HashMap<>();
		List<Product> products = (List<Product>) productRepository.findAllById(productIds);
		products.stream().forEach(x -> itemAndPrice.put(x.getProductId(), x.getPrice()));
		
		float total = 0;
		for (UserCart cart : userCartProduct) {
			total = total + cart.getQuantity() * itemAndPrice.get(cart.getProductId());
		}
		
		UserOrder order = new UserOrder();
		order.setTotal(total);
		order.setUserId(userId);
		order.setDateTime(new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Date()));
		
		userOrderRepository.save(order);
		
		userCartRepository.deleteAll(userCartProduct);
		
		return "SUCCESS";
	}

	private boolean isPaymentSuccessful() {
		Random random = new Random();
		
		return random.ints(5, 10).findFirst().getAsInt() > 7;
	}
}
