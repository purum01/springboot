package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.Order;

public interface OrderRepository  extends JpaRepository<Order, Long>{

}
