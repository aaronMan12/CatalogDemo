package com.catalogs.demo.product.repository;

import com.catalogs.demo.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}


