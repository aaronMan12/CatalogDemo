package com.catalogs.demo.product.service;

import com.catalogs.demo.product.dto.ImageUpload;
import com.catalogs.demo.product.entity.ImageProducts;
import com.catalogs.demo.product.repository.ImageProductRepository;
import com.catalogs.demo.product.service.repository.StorageService;
import com.catalogs.demo.product.service.storage.CloudinaryStorageService;
import com.catalogs.demo.response.ResponseMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

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

    public ResponseMessage saveProductImage(MultipartFile image, Integer productId) {
        int countImages = imageProductRepository.countByProductId(productId);

        if (countImages >= MAX_IMAGES_PER_PRODUCT) {
            return new ResponseMessage(400, "Solo se pueden subir máximo " + MAX_IMAGES_PER_PRODUCT + " imágenes por producto");
        }

        try {
            String folderPath = buildFolderPath(productId, countImages);
            ResponseMessage uploadResult = storageService.uploadFile(image, folderPath);

            if (uploadResult.getStatus() == 201) {
                ImageProducts savedImage = imageProductRepository.save(
                        new ImageProducts(productId, (ImageUpload) uploadResult.getData())
                );
                return new ResponseMessage(200, "Imagen subida exitosamente", savedImage);
            }
            return new ResponseMessage(400, "Error al subir imagen", uploadResult.getErrors());

        } catch (Exception e) {
            return handleException("Error en subir archivos: ", e);
        }
    }


    public ResponseMessage updateProductImage(MultipartFile image, Integer productId, Integer position) {
        if (position < 1 || position > MAX_IMAGES_PER_PRODUCT) {
            return new ResponseMessage(400, "Posición debe ser entre 1 y " + MAX_IMAGES_PER_PRODUCT);
        }

        try {
            ImageProducts existingImage = getImageByPosition(productId, position);
            if (existingImage == null) {
                return new ResponseMessage(404, "No existe imagen en la posición " + position);
            }

            // Eliminar imagen anterior
            if (!storageService.deleteFile(existingImage.getPublicId())) {
                System.out.println("Advertencia: No se pudo eliminar imagen anterior de Cloudinary");
            }

            // Subir nueva imagen
            ResponseMessage uploadResult = storageService.uploadFile(image,
                    productId.toString() + "/" + position);

            if (uploadResult.getStatus() == 201) {
                ImageUpload newImageData = (ImageUpload) uploadResult.getData();
                existingImage.getNewImage(newImageData);

                ImageProducts updatedImage = imageProductRepository.save(existingImage);
                return new ResponseMessage(200, "Imagen actualizada exitosamente", updatedImage);
            }
            return new ResponseMessage(400, "Error al subir imagen", uploadResult.getErrors());

        } catch (Exception e) {
            return handleException("Error actualizando imagen: ", e);
        }
    }

    private String buildFolderPath(Integer productId, int currentImageCount) {
        int nextPosition = (currentImageCount == 0) ? 1 : currentImageCount + 1;
        return productId.toString() + "/" + nextPosition;
    }

    private ImageProducts getImageByPosition(Integer productId, Integer position) {
        List<ImageProducts> productImages = imageProductRepository.findProductImagesByIdProductAsc(productId);
        return (position <= productImages.size()) ? productImages.get(position - 1) : null;
    }

    private ResponseMessage handleException(String message, Exception e) {
        System.out.println(message + e.getMessage());
        return new ResponseMessage(500, "Error interno: " + e.getMessage());
    }
}
