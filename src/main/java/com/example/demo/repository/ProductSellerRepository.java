package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductSeller;

@Repository
public interface ProductSellerRepository extends PagingAndSortingRepository<ProductSeller, Integer>{
	
}
