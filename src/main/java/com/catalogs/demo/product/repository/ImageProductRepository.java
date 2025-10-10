package com.catalogs.demo.product.repository;

import com.catalogs.demo.product.entity.ImageProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ImageProductRepository extends JpaRepository<ImageProducts, Long> {
    @Query(value = """
            select
            	count(ip.id_product)
            from dbo.image_products ip
            where ip.id_product = ?1
            """, nativeQuery = true)
    int countByProductId(Integer idProduct);

    @Query(value = """
            select *
            from dbo.image_products ip
            where ip.id_product = ?1
            order by ip.creation_time
            """, nativeQuery = true)
    List<ImageProducts> findProductImagesByIdProductAsc(Integer idProduct);
}
