package com.catalogs.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseMessage {
    private Integer status;
    private String message;
    private Object data;
    private List<Map<String, String>> errors;

    public ResponseMessage(String message, List<Map<String, String >> errors){
        this.message = message;
        this.errors = errors;
    }

    public ResponseMessage(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    public ResponseMessage(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(String message){
        this.message = message;
    }

}
