package com.catalogs.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogingResponseDto {
    private Integer idUSer;
    private String name;
    private String userName;
    private String fullName;
    private String firstLastName;
    private String secondLastName;
    private String token;

    public LogingResponseDto(Integer idUSer, String name, String userName, String fullName, String firstLastName, String secondLastName) {
        this.idUSer = idUSer;
        this.name = name;
        this.userName = userName;
        this.fullName = fullName;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
    }
}
