package com.catalogs.demo.product.repository;

import com.catalogs.demo.product.entity.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    @Query(value = """
            select
            	count(ip.id_product)
            from dbo.image_products ip
            where ip.id_product = ?1
            """, nativeQuery = true)
    int countByProductId(Integer idProduct);
}
