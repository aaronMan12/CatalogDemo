package com.catalogs.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Data
public class UserRegisterDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private  Integer idUser;

    @NotNull(message = "El estatus es obligatorio")
    @Column(name = "id_status")
    private Integer idStatus;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Column(name = "first_last_name")
    private String firstLastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    @NotBlank(message = "El nombre de usuario es Obligatorio")
    @Column(name = "user_name")
    private String userName;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener más de 8 caracteres")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no valido")
    @Column(name = "email")
    private String email;

}