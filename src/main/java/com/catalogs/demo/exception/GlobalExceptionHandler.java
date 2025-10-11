package com.catalogs.demo.exception;

import com.catalogs.demo.response.ResponseMessage;
import org.apache.catalina.authenticator.NonLoginAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException( MaxUploadSizeExceededException exc) {
        String errorMessage = String.format(
                "El archivo excede el tamaño máximo permitido de %s", maxSize);

        return ResponseEntity.status(413) // 413 Payload Too Large
                .body(new ResponseMessage(413, errorMessage));
    }

   /* @ExceptionHandler(NonLoginAuthenticator.class)
    public ResponseEntity<ResponseMessage> handleNoAutozation(NonLoginAuthenticator exc){
        return ResponseEntity.status(413) // 413 Payload Too Large
                .body(new ResponseMessage(413, "Usuario no autorizado"));
    }*/
}
