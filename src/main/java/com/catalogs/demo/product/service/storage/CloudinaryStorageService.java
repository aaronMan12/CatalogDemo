package com.catalogs.demo.product.service.storage;

import com.catalogs.demo.product.dto.ImageUpload;
import com.catalogs.demo.product.service.repository.StorageService;
import com.catalogs.demo.response.ResponseMessage;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("cloudinaryStoreService")
public class CloudinaryStorageService implements StorageService {

    @Autowired
    Cloudinary cloudinary;  //= C;

    @Override
    public ResponseMessage uploadFile(MultipartFile file, String folderPath) {
        try {
            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("folder", "mvp/products/" + folderPath);
            uploadParams.put("public_id", generatePublicId(file.getOriginalFilename()));
            uploadParams.put("resource_type", "image");
            uploadParams.put("quality", "auto:good");
            uploadParams.put("fetch_format", "auto");

            //Subír archivos a cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    uploadParams
            );

            String imageUrl = (String) uploadResult.get("secure_url");
            String publicUrl = (String) uploadResult.get("public_id");

            ImageUpload imagenSubída = new ImageUpload(imageUrl, publicUrl);

            if (!imageUrl.isEmpty()){
                return new ResponseMessage(201, "Imagen subída corractamente", imagenSubída);
            }else{
                return  new ResponseMessage(402, "Error al subír la imagen");
            }

        }catch (Exception e){
            System.err.println("❌ Error en Cloudinary: " + e.getMessage());
            e.printStackTrace();
            return new ResponseMessage(500, "Error al subir la imagen: " + e.getMessage());
        }

    }

    @Override
    public ResponseMessage deleteFile(String publicId) {  // Cambié el parámetro a publicId
        try {
            Map<String, Object> deleteResult = cloudinary.uploader()
                    .destroy(publicId, ObjectUtils.emptyMap());

            //cloudinary.uploader().destroy(publicId, );

            String result = (String) deleteResult.get("result");

            System.out.println("Resultado de la eliminación: " + result);

            if ("ok".equals(result)) {
                return new ResponseMessage(200, "Imagen eliminada correctamente");
            } else {
                return new ResponseMessage(400, "No se pudo eliminar la imagen. Resultado: " + result);
            }
        } catch (Exception e) {
            return new ResponseMessage(500, "Error al borrar la imagen: " + e.getMessage());
        }
    }

    private String generatePublicId(String originalFileName) {
        String baseName = "product";
        if (originalFileName != null && originalFileName.contains(".")) {
            baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomId = UUID.randomUUID().toString().substring(0, 8);

        // Ejemplo: "product_1645678901234_abc123"
        return baseName + "_" + timestamp + "_" + randomId;
    }
}
