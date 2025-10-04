package com.catalogs.demo.product.controller;

import com.catalogs.demo.product.service.ImageStorageService;
import com.catalogs.demo.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product/image")
public class ImageProductController {
    @Autowired
    ImageStorageService storageService;

    @PostMapping("/upload/{productId}")
    public ResponseEntity<ResponseMessage> uploadImage(
            @PathVariable Integer productId,
            @RequestParam("image")MultipartFile image,
            @RequestParam(defaultValue = "1") Integer position) {
        //Todo-aquí se validara que sea del tipo imagen, que no sobrepase los 5MG y que sea valida.

        try {
            ResponseMessage result = storageService.saveProductImage(image, productId, position);
            return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error de servidor:" +e.getMessage()));
        }
    }

}
