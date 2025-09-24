package com.catalogs.demo.catalog.repository;

import com.catalogs.demo.catalog.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    @Modifying
    @Query(value = """
            update dbo.products_catalog set
            id_catalog_type = ?1,
            name = ?2
            where id_products_catalog = ?3 and id_user = ?4
            """, nativeQuery = true)
    int editCatalog(Integer idCatalogType, String name, Integer idProductsCatalog, Integer idUser);
}
