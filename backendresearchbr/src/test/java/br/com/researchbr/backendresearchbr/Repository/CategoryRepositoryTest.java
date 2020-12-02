package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.CategoryEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleType;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository repository;

    @AfterEach
    public void clean() {
        repository.deleteAll();
    }

    @Test
    public void saveCategory() {

        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");
        repository.save(category);

        assertEquals(category.getName(), repository.findByName("Material permanente").getName());
    }

    @Test
    public void findByCategoryName() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");

        assertNull(repository.findByName("Material permanente"));
    }

    @Test
    public void editCategory() {

        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");
        CategoryEntity newCategoryEntity = repository.save(category);
        newCategoryEntity.setName("Material de consumo");
        repository.save(newCategoryEntity);

        assertNull(repository.findByName("Material permanente"));
        assertTrue("Material de consumo".equals(repository.findByName("Material de consumo").getName()));
    }

}
