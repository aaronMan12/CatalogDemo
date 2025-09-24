package com.catalogs.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer idUser;
    private Integer idStatus;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String userName;
    private String email;
    private boolean verifiedEmail;
    private LocalDateTime creationTime;
}
