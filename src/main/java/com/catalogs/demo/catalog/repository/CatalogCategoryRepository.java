package com.catalogs.demo.catalog.repository;

import com.catalogs.demo.catalog.entity.CatalogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CatalogCategoryRepository extends JpaRepository<CatalogCategory, Long> {
    @Query(value = """
            select 1
            from dbo.catalog_category cc
            where cc.id_catalog_category = ?1
            """, nativeQuery = true)
    Integer existCatalogCategory(Integer type);
}
