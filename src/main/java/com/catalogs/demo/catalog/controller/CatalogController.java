package com.catalogs.demo.catalog.controller;

import com.catalogs.demo.catalog.dto.CatalogEditDto;
import com.catalogs.demo.catalog.dto.CatalogRegisterDto;
import com.catalogs.demo.catalog.entity.Catalog;
import com.catalogs.demo.catalog.repository.CatalogRepository;
import com.catalogs.demo.catalog.service.CatalogService;
import com.catalogs.demo.response.ResponseMessage;
import com.catalogs.demo.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> registerCatalog(@Valid @RequestBody CatalogRegisterDto catalog, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()){
                List<Map<String, String>> errorList = Utils.getErrors(bindingResult);
                return ResponseEntity.status(400).body(new ResponseMessage("Error de validación", errorList));
            }
            ResponseMessage result = catalogService.registerCatalog(catalog);
            return ResponseEntity.status(result.getStatus()).body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error en servidor: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<ResponseMessage> updateCatalog(@Valid @RequestBody CatalogEditDto catalog, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()){
                List<Map<String, String>> errorList = Utils.getErrors(bindingResult);
                return ResponseEntity.status(400).body(new ResponseMessage("Error de validación", errorList));
            }
            ResponseMessage result = catalogService.editCatalog(catalog);
            if (result != null){
                return ResponseEntity.status(200).body(new ResponseMessage(("Catálogo actualizado")));
            }else {
                return ResponseEntity.status(400).body(new ResponseMessage("No se pudo actualizar el catálogo: "));
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ResponseMessage("Error en servidor: " + e.getMessage()));
        }
    }
}
