package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.UserOrder;

@Repository
public interface UserOrderRepository extends CrudRepository<UserOrder, Long> {

}
