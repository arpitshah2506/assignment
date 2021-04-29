package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ProductDTO;
import com.example.demo.model.ProductSellerDTO;
import com.example.demo.model.UserCartDTO;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserCartService;

@RestController
public class EcomAppController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserCartService userCartService;
	
	@GetMapping(value = "/products",produces = {
	        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE })
	public List<ProductDTO> getProducts(@RequestParam(defaultValue = "0", required = false) int pageNo)
	{
		return productService.getProducts(pageNo);
	}
	
	@PostMapping("/products")
	public ResponseEntity<String> addProducts(@RequestBody ProductSellerDTO sellerProducts) {
		productService.addProducts(sellerProducts);
		return new ResponseEntity<String>("Products posted", HttpStatus.OK);
	}
	
	@PostMapping("/cart")
	public ResponseEntity<String> addToCart(@RequestBody UserCartDTO userCart) {
		if (userCartService.addToCart(userCart)) {
			return new ResponseEntity<String>("Item(s) added to cart", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Item(s) not added to cart", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/order")
	public ResponseEntity<String> placeOrder(@RequestParam(required = true, name = "userId") long userId) {
		String orderStatus = userCartService.placeOrder(userId);
		if ("SUCCESS".equalsIgnoreCase(orderStatus)) {
			return new ResponseEntity<String>("Order placed successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>(orderStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
