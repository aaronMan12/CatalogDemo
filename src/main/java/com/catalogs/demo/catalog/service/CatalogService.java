package com.catalogs.demo.catalog.service;

import com.catalogs.demo.catalog.dto.CatalogRegisterEditDto;
import com.catalogs.demo.catalog.entity.Catalog;
import com.catalogs.demo.catalog.entity.CatalogCategory;
import com.catalogs.demo.catalog.repository.CatalogCategoryRepository;
import com.catalogs.demo.catalog.repository.CatalogRepository;
import com.catalogs.demo.response.ResponseMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogCategoryRepository catalogCategoryRepository;

    public ResponseMessage registerCatalog(CatalogRegisterEditDto catalog){
        //TODO-Vefificar que el usuario que va a registrar el catalogo exista
        try{
        Integer existCategory = createCategoryCatalog(catalog);

        Catalog newCatalog = new Catalog();
        newCatalog.setIdUser(catalog.getIdUser());
        newCatalog.setIdCatalogType((existCategory != null) ?  existCategory : catalog.getIdCatalogType());
        newCatalog.setName(catalog.getName());
        Catalog catalogCreated = catalogRepository.save(newCatalog);

        return catalogCreated != null
                    ? new ResponseMessage(201, "Catalogo creado exitosamente")
                    : new ResponseMessage(400, "Error al crear el catálogo");
        }catch (Exception e){
            return new ResponseMessage(500, "Error de servidor");
        }
    }

    public ResponseMessage editCatalog(@Valid CatalogRegisterEditDto catalog) {

        try {
            Optional<Catalog> existCatalog  = catalogRepository.findById(Long.valueOf(catalog.getIdProductsCatalog()));
            if (existCatalog.isEmpty()){
                return new ResponseMessage(404, "Catálogo no encontrado");
            }
            //TODO, vefificar si existe la categoria, si no crearla
            Integer existCategory = createCategoryCatalog(catalog);

            Catalog catalogEditado = existCatalog.get();
            catalogEditado.setIdCatalogType((existCategory != null) ?  existCategory : catalog.getIdCatalogType());
            catalogEditado.setName(catalog.getName());

            Catalog catalogEdited = catalogRepository.save(catalogEditado);

            return (catalogEdited != null) ? new ResponseMessage(201, "Catalogo editado exitosamente") : new ResponseMessage(400, "Error al editar catálogo");
        } catch (Exception e) {
            return new ResponseMessage(500, "Error de servidor");
        }
    }

    private Integer createCategoryCatalog(CatalogRegisterEditDto catalog){
        Integer validCategory = catalogCategoryRepository.existCatalogCategory(catalog.getIdCatalogType());
        if (validCategory == null) {
            CatalogCategory reqNewCategory = new CatalogCategory();
            reqNewCategory.setName(catalog.getCategory().getName());
            reqNewCategory.setDescription(catalog.getCategory().getDescription());

            CatalogCategory newCategory = catalogCategoryRepository.save(reqNewCategory);
            if (newCategory != null){
                return newCategory.getIdCatalogCategory();
            }
        }
        return null;
    }
}
