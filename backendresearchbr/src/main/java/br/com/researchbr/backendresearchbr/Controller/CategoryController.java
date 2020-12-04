package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.Entity.CategoryEntity;
import br.com.researchbr.backendresearchbr.Service.Impl.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping(value = "/all")
    public List<CategoryEntity> listAll(){
        log.info("Finding categories.");
        log.warn("The list can return null if no items are available!");
        return categoryService.all();
    }

}
