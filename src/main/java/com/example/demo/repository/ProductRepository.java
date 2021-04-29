package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository(value = "productRepository")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{
	
}
