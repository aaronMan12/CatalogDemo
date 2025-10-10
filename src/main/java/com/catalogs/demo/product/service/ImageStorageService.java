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
            //1. validación del máximo de imagenes por producto, 2 Máximo actualmente
            int countImages = validateMaxImages(productId);

            if (countImages >= MAX_IMAGES_PER_PRODUCT) {
                return new ResponseMessage(400, "Solo se pueden subir máximo " + MAX_IMAGES_PER_PRODUCT + " imágenes por producto");
            }

        try {
            ResponseMessage uploadResult = new ResponseMessage();
            //2. Setear en que posición se sibíra la imagen y subírla
            if (countImages == 0){
                uploadResult = storageService.uploadFile(image, productId.toString()+"/"+1);
            }else {
                Integer position = countImages +1;
                uploadResult = storageService.uploadFile(image, productId.toString()+"/"+position.toString());
            }

            //Insertar la imagen en la base de datos
            if (uploadResult.getStatus() == 201) {
                ImageProducts newImageSaved = new ImageProducts(productId, (ImageUpload) uploadResult.getData());
                ImageProducts imageSaved = imageProductRepository.save(newImageSaved);
                    return new ResponseMessage(200, "Imagen subida exitosamente", imageSaved);
            } else {
                return new ResponseMessage(400, "Error al subir imagen", uploadResult.getErrors());
            }

        } catch (Exception e) {
            System.out.println("Error en subír archivos: " + e.getMessage());
            return new ResponseMessage(500, "Error interno: " + e.getMessage());
        }
    }


    public ResponseMessage updateProductImage(MultipartFile image, Integer productId, Integer position) {
        if (position < 1 || position > 2){
            return new ResponseMessage(400, "Posición debe ser entre 1 y 2");
        }

        //1.- Obtener las imagenes que ya estan guardadas
        List<ImageProducts> productImages = imageProductRepository.findProductImagesByIdProductAsc(productId);

        ImageProducts productsForUpdate = productImages.get(position-1);

        //2.- Eliminar de Cloudinary el recurso actual
        System.out.println("Eliminar este recurso: " + productsForUpdate.getImgLink());

        String oldPublicId = productsForUpdate.getPublicId();

        if (!oldPublicId.isEmpty()){
            storageService.deleteFile(oldPublicId);
        }

        //3.- Subír el nuevo recurso

        //4.- Recuperar el nuevo URL y actulaizar el objeto

        //5.- Mandar si es que se actualizo la imagen correctamente


        return new ResponseMessage(200, "Test de actualización de imagenes");
    }

    private int validateMaxImages(Integer productId) {
        return imageProductRepository.countByProductId(productId);
    }
}
