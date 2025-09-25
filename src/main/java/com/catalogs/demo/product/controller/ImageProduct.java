package com.catalogs.demo.product.controller;

import com.catalogs.demo.product.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/image")
public class ImageProduct {
    @Autowired
    ImageProductService productService;

}
