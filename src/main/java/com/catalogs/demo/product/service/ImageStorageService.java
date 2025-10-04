package com.catalogs.demo.product.service;

import com.catalogs.demo.product.repository.ImageProductRepository;
import com.catalogs.demo.product.service.repository.StorageService;
import com.catalogs.demo.product.service.storage.CloudinaryStorageService;
import com.catalogs.demo.response.ResponseMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ImageStorageService {
    @Autowired
    private ImageProductRepository imageProductRepository;

    private final StorageService storageService;
    private static final int MAX_IMAGES_PER_PRODUCT = 2;

    @Autowired
    private CloudinaryStorageService cloudinaryStorageService;

    @Autowired
    public ImageStorageService(@Qualifier("cloudinaryStoreService") StorageService storageService){
        this.storageService = storageService;
    }

    public ResponseMessage saveProductImage(MultipartFile image, Integer productId, Integer position) {

        try {
            // 1. Validar máximo de imágenes
            if (validateMaxImages(productId)) {
                return new ResponseMessage(400, "Solo se pueden subir máximo " + MAX_IMAGES_PER_PRODUCT + " imágenes por producto");
            }

            // 2. Subir imagen usando storageService
            ResponseMessage uploadResult = storageService.uploadFile(image, productId.toString());

            // 3. Si se subió correctamente, guardar en BD
            if (uploadResult.getStatus() == 200 || uploadResult.getStatus() == 201) {
                // Aquí guardarías en imageProductRepository
                return new ResponseMessage(200, "Imagen subida exitosamente", uploadResult.getData());
            } else {
                return uploadResult; // Retornar el error
            }

        } catch (Exception e) {
            return new ResponseMessage(500, "Error interno: " + e.getMessage());
        }
    }

    private boolean validateMaxImages(Integer productId) {
        long count = imageProductRepository.countByProductId(productId);
        return count >= MAX_IMAGES_PER_PRODUCT;
    }
}
