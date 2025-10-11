package com.catalogs.demo.product.controller;

import com.catalogs.demo.product.service.ImageStorageService;
import com.catalogs.demo.response.ResponseMessage;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageProductController {
    @Autowired
    ImageStorageService storageService;
    private List<String> typesAccepted = List.of("image/jpeg","image/png","image/webp","image/gif");

    @PostMapping("/product/{productId}")
    public ResponseEntity<ResponseMessage> uploadImage(
            @PathVariable Integer productId,
            @RequestParam("image")MultipartFile image,
            @RequestParam(defaultValue = "0") Integer position ) {
        //TODO.- Validar el tipo de la imagen.
        if (!typesAccepted.contains(image.getContentType())){
            return ResponseEntity.status(400).body(new ResponseMessage(400, "Solo se aceptan formatos (jpeg,png,webp,gif)"));
        }
        try {
            ResponseMessage result = (position != 0)
                    ? storageService.updateProductImage(image, productId, position)
                    : storageService.saveProductImage(image, productId);
            return ResponseEntity.status(result.getStatus()).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ResponseMessage(500, "Error de servidor: " + e.getMessage()));
        }
    }
}

