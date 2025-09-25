package com.catalogs.demo.product.entity;

import com.catalogs.demo.product.dto.ProductRegisterEditDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer idProduct;

    @Column(name = "id_products_catalog")
    private Integer idProductsCatalog;

    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "stock")
    private Integer stock;

}
