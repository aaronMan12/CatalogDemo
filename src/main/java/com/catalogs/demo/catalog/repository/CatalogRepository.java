package com.catalogs.demo.catalog.repository;

import com.catalogs.demo.catalog.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CatalogRepository extends JpaRepository<Catalog, Long> { }
