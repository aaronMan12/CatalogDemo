package com.catalogs.demo.catalog.service;

import com.catalogs.demo.catalog.dto.CatalogEditDto;
import com.catalogs.demo.catalog.dto.CatalogRegisterDto;
import com.catalogs.demo.catalog.entity.Catalog;
import com.catalogs.demo.catalog.entity.CatalogCategory;
import com.catalogs.demo.catalog.repository.CatalogCategoryRepository;
import com.catalogs.demo.catalog.repository.CatalogRepository;
import com.catalogs.demo.response.ResponseMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogCategoryRepository catalogCategoryRepository;

    public ResponseMessage registerCatalog(CatalogRegisterDto catalog){
        //TODO-Vefificar que el usuario que va a registrar el catalogo exista
        try{
        Integer validCategory = catalogCategoryRepository.existCatalogCategory(catalog.getIdCatalogType());

        if (validCategory == null) {
            CatalogCategory reqNewCategory = new CatalogCategory();
            reqNewCategory.setName(catalog.getCategory().getName());
            reqNewCategory.setDescription(catalog.getCategory().getDescription());

            CatalogCategory newCategory = catalogCategoryRepository.save(reqNewCategory);
            if (newCategory != null){
                System.out.println("Categoria creada");
                catalog.setIdCatalogType(newCategory.getIdCatalogCategory());
            }
        }
        Catalog newCatalog = new Catalog();
        newCatalog.setIdUser(catalog.getIdUser());
        newCatalog.setIdCatalogType(catalog.getIdCatalogType());
        newCatalog.setName(catalog.getName());
        Catalog catalogCreated = catalogRepository.save(newCatalog);

        if (catalogCreated != null){
            return new ResponseMessage(201, "Catalogo creado exitosamente");
        }else {
            return new ResponseMessage(400, "Error al crear el catálogo");
        }
        }catch (Exception e){
            return new ResponseMessage(500, "Error de servidor");
        }
    }

    public ResponseMessage editCatalog(@Valid CatalogEditDto catalog) {
        //TODO, verificar si la categoria existe, si no es así registrarla
        Integer catlogEditaded = catalogRepository.editCatalog(catalog.getIdCatalogType(), catalog.getName(), catalog.getIdProductsCatalog(), catalog.getIdUser());
        return new ResponseMessage(200, "");
    }
}
