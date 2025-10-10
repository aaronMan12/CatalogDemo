package com.catalogs.demo.product.controller;

import com.catalogs.demo.product.service.ImageStorageService;
import com.catalogs.demo.response.ResponseMessage;
import com.cloudinary.Cloudinary;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageProductController {
    @Autowired
    ImageStorageService storageService;


    @Autowired
    private Cloudinary cloudinary;

    @Value("${app.storage.max-file-size}")
    private int maxSize;

    @PostMapping("/product/{productId}")
    public ResponseEntity<ResponseMessage> uploadImage(
            @PathVariable Integer productId,
            @RequestParam("image")MultipartFile image,
            @RequestParam(defaultValue = "0") Integer position ) {
        //Todo-aquí se validara que sea del tipo imagen, que no sobrepase los 5MG y que sea valida.

        if (position != 0){
            //TODO.- Acualizar la posición que se indique.
            try {
                ResponseMessage result = storageService.updateProductImage(image, productId, position);
                return ResponseEntity.status(result.getStatus()).body(result);

            }catch (Exception e){
                return ResponseEntity.status(500).body(new ResponseMessage("Error de servidor:" +e.getMessage()));
            }
            //return ResponseEntity.status(200).body(new ResponseMessage(200,"Se actuliza la imagen indicada en la position en este caso solo puede ser 1 o 2"));
        }else{
        try {
            ResponseMessage result = storageService.saveProductImage(image, productId);
            return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error de servidor:" +e.getMessage()));
        }
        }
    }

}

