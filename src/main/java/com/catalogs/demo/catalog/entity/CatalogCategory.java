package com.catalogs.demo.catalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "catalog_category", schema = "dbo")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalog_category")
    private Integer idCatalogCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
