package com.catalogs.demo.product.service;

import com.catalogs.demo.product.dto.ProductRegisterEditDto;
import com.catalogs.demo.product.entity.Product;
import com.catalogs.demo.product.repository.ProductRepository;
import com.catalogs.demo.response.ResponseMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private static final Integer createOption = 1;
    private static final Integer editOption = 2;


    public ResponseMessage registerProduct(@Valid ProductRegisterEditDto product) {
        try {
            Product newProduct = createEditProductFormat(product, createOption);

            Product createdProduct = productRepository.save(newProduct);

            if (createdProduct != null) {
                return new ResponseMessage(200, "Producto creado", createdProduct);
            } else {
                return new ResponseMessage(400, "No se pudo registrar el producto");
            }
        } catch (Exception e) {
            return new ResponseMessage(500, "Error en servidor: " + e.getMessage());
        }
    }

    public ResponseMessage editProduct(@Valid ProductRegisterEditDto product) {
        try {
            Optional<Product> existProduct = productRepository.findById(Long.valueOf(product.getIdProduct()));

            if (existProduct.isEmpty()){
                return new ResponseMessage(404, "El producto no encontrado");
            }

            Product newProduct = createEditProductFormat(product, editOption);
            newProduct.setIdProductsCatalog(existProduct.get().getIdProductsCatalog());

            Product editedProduct = productRepository.save(newProduct);

            if (editedProduct != null) {
                return new ResponseMessage(200, "Producto editado", editedProduct);
            } else {
                return new ResponseMessage(400, "No se pudo editar el producto");
            }
        } catch (Exception e) {
            return new ResponseMessage(500, "Error en servidor: " + e.getMessage());
        }
    }

    private static Product createEditProductFormat(ProductRegisterEditDto product, Integer oper) {
        Product newProduct = new Product();
        newProduct.setIdStatus(product.getIdStatus());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setStock(product.getStock());
        if (oper == 1 ) newProduct.setIdProductsCatalog(product.getIdProductsCatalog());
        if (oper == 2 ) newProduct.setIdProduct(product.getIdProduct());
        return newProduct;
    }

}