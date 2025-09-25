package com.catalogs.demo.catalog.dto;

import com.catalogs.demo.catalog.entity.CatalogCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogRegisterEditDto {
    private Integer idProductsCatalog;

    @NotNull(message = "El id del usuario es obligatorio")
    private Integer idUser;

    private Integer idCatalogType;

    @NotBlank(message = "El nombre del catálogo es obligatorio")
    private String name;

    private CatalogCategory category;

}

