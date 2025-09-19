package com.catalogs.demo.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idClient;

    private Integer idStatus;

    private String name;
    private String firstName;
    private String secondLastName;
    private String userName;
    private String password;
    private String email;
}
