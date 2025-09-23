package com.catalogs.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private Integer idStatus;

    private String name;

    private String firstLastName;

    private String secondLastName;

    private String userName;

    private String password;

    private String email;
}
