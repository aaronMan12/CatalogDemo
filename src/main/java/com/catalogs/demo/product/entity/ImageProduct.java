package com.catalogs.demo.product.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image_products", schema = "dbo")
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image_product")
    private Integer idImageProduct;

    @Column(name = "id_product")
    private Integer idProduct;

    @Column(name = "img_link")
    private String imgLink;
}
