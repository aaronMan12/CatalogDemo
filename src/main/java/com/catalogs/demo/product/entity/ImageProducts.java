package com.catalogs.demo.product.entity;

import com.catalogs.demo.product.dto.ImageUpload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "image_products", schema = "dbo")
@AllArgsConstructor
@NoArgsConstructor
public class ImageProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image_product")
    private Integer idImageProduct;

    @Column(name = "id_product")
    private Integer idProduct;

    @Column(name = "img_link")
    private String imgLink;

    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "public_id")
    private String publicId;

    public ImageProducts(Integer idProduct, String imgLink, String publicId){
        this.idProduct = idProduct;
        this.imgLink = imgLink;
        this.publicId = publicId;
    }

    public ImageProducts(Integer idProduct, ImageUpload imageUpload){
        this.idProduct = idProduct;
        this.imgLink = imageUpload.getImgLink();
        this.publicId = imageUpload.getPublicId();
    }
}
