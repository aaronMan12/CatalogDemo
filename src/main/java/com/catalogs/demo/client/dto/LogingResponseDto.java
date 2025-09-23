package com.catalogs.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogingResponseDto {
    private String name;
    private String userName;
    private String fullName;
    private String firstLastName;
    private String secondLastName;
    private boolean success;
    private String message;

    public LogingResponseDto(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
