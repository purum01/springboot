package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
