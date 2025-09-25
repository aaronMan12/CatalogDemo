package com.catalogs.demo.product.controller;

import com.catalogs.demo.product.dto.ProductRegisterEditDto;
import com.catalogs.demo.product.service.ProductService;
import com.catalogs.demo.response.ResponseMessage;
import com.catalogs.demo.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseMessage> registerProduct(@Valid @RequestBody ProductRegisterEditDto product, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()){
                List<Map<String, String>> errorList = Utils.getErrors(bindingResult);
                return ResponseEntity.status(400).body(new ResponseMessage("Error de validación", errorList));
            }
            ResponseMessage result = productService.registerProduct(product);
            return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error en servidor: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<ResponseMessage> editProduct(@Valid @RequestBody ProductRegisterEditDto product, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()){
                List<Map<String, String>> errorList = Utils.getErrors(bindingResult);
                return ResponseEntity.status(400).body(new ResponseMessage("Error de validación", errorList));
            }
            ResponseMessage result = productService.editProduct(product);
            return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error en servidor: " + e.getMessage()));
        }
    }

}
