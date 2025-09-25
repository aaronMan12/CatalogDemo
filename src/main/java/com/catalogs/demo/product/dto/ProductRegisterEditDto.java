package com.catalogs.demo.product.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegisterEditDto {
    private Integer idProduct;

    private Integer idProductsCatalog;

    @NotNull(message = "El eststus del producto es obligatorio")
    private Integer idStatus;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    @NotBlank(message = "La descripción del producto es obligatoria")
    private String description;

    @NotNull(message = "El precio del producto es obligatorio")
    @Min(value = 0, message = "El producto debe tener un precio mayor a $0")
    private Float price;

    @NotNull(message = "El stock es obligatorio")
    @Min( value = 0, message = "El stock debe se mayor a 0")
    private Integer stock;
}
