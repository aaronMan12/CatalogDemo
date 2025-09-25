package com.catalogs.demo.product.repository;

import com.catalogs.demo.product.controller.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {}
