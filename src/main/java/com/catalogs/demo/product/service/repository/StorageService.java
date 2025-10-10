package com.catalogs.demo.product.service.repository;

import com.catalogs.demo.response.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    ResponseMessage uploadFile(MultipartFile file, String folderPath);
    ResponseMessage deleteFile(String fileUrl);
    //ResponseMessage getFileUrl(String filePath);
}
