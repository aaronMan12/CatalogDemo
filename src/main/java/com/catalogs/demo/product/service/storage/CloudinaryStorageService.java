package com.catalogs.demo.product.service.storage;

import com.catalogs.demo.product.service.repository.StorageService;
import com.catalogs.demo.response.ResponseMessage;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("cloudinaryStoreService")
public class CloudinaryStorageService implements StorageService {

    @Autowired
    Cloudinary cloudinary;

    @Override
    public ResponseMessage uploadFile(MultipartFile file, String folderPath) {
        try {
            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("folder", "mvp/products/" + folderPath);
            uploadParams.put("public_id", generatePublicId(file.getOriginalFilename()));
            uploadParams.put("resource_type", "image");

            // Optimizaciones automáticas
            uploadParams.put("quality", "auto:good");
            uploadParams.put("fetch_format", "auto");

            //Subír archivos a cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    uploadParams
            );

            String imageUrl = (String) uploadResult.get("secure_url");

            if (imageUrl.isEmpty()){
                return new ResponseMessage(201, "Imagen subída corractamente", imageUrl);
            }else{
                return  new ResponseMessage(402, "Error al subír la imagen");
            }

        }catch (Exception e){
            return new ResponseMessage(500, "Error al subír la imagen:" + e.getMessage());
        }

    }

 /*   @Override
    public ResponseMessage deleteFile(String fileUrl) {
        try{
            String publicId = extractPublicIdFromUrl(fileUrl);
            Map<String, Object> deleteResult = cloudinary.uploader()
                    .destroy(publicId, ObjectUtils.emptyMap());

            String res = (String) deleteResult.get("result");
            if (res.equals("ok")){
                return new ResponseMessage(200, "Imagen eliminada correctamente");
            } else {
                return new ResponseMessage(400, "No se pudo eliminar la imagen");
            }
        }catch (Exception e){
            return new ResponseMessage(500, "Error al borrar la imagen:" + e.getMessage());
        }
    }

    @Override
    public ResponseMessage getFileUrl(String filePath) {
        try {
            String url = cloudinary.url().generate(filePath);
            return new ResponseMessage(200, "URL generada", url);
        } catch (Exception e) {
            return new ResponseMessage(500, "Error generando URL: " + e.getMessage());
        }
    }
*/
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

  /*  private String extractPublicIdFromUrl(String fileUrl) {
        try {
            String basePath = "mvp/products/";
            int baseIndex = fileUrl.indexOf(basePath);
            if (baseIndex != -1) {
                String pathWithExtension = fileUrl.substring(baseIndex);
                return pathWithExtension.substring(0, pathWithExtension.lastIndexOf('.'));
            }
            throw new RuntimeException("No se pudo extraer public_id de la URL");
        } catch (Exception e) {
            throw new RuntimeException("Error extrayendo public_id: " + e.getMessage());
        }
    }*/

}
