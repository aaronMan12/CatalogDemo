package com.catalogs.demo.catalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "products_catalog", schema = "dbo")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_products_catalog")
    private Integer idProductsCatalog;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "id_catalog_type")
    private Integer idCatalogType;

    @Column(name = "name")
    private String name;
}
