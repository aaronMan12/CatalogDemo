package com.catalogs.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseMessage {
    private String message;
    private List<Map<String, String>> errors;

    public ResponseMessage(String message, List<Map<String, String >> errors){
        this.message = message;
        this.errors = errors;
    }

    public ResponseMessage(String message){
        this.message = message;
    }
}
