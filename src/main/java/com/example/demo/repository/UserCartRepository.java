package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserCart;

@Repository
public interface UserCartRepository extends CrudRepository<UserCart, Long>, UserCartCustom{
	
}
