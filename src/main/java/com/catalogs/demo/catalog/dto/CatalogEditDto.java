package com.catalogs.demo.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CatalogEditDto {
    @NotNull(message = "La categoría del catálogo es obligatorio")
    private Integer idCatalogType;

    @NotBlank(message = "El nombre del catálogo es obligatorio")
    private String name;

    @NotNull(message = "El identificador del catálogo es obligatorio")
    private Integer idProductsCatalog;

    @NotNull(message = "El identificador del usuario es obligatorio")
    private Integer idUser;
}
