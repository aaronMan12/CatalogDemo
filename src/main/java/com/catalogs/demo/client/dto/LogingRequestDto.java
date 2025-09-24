package com.catalogs.demo.client.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogingRequestDto {
    private String userName;
    private String password;
}
