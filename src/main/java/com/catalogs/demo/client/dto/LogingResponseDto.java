package com.catalogs.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogingResponseDto {
    private String name;
    private String userName;
    private String fullName;
    private String firstLastName;
    private String secondLastName;
    private String token;

    public LogingResponseDto(String name, String userName, String fullName, String firstLastName, String secondLastName) {
        this.name = name;
        this.userName = userName;
        this.fullName = fullName;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
    }
}
