package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    public void setup() {
        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.PRINCIPAL);
        RoleEntity roleEntity = roleRepository.save(roles);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleEntity);
        UserEntity userEntity1 = userRepository.save(userEntity);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCode("123456/2020");
        projectEntity.setAgency("CNPq");
        projectEntity.setTitle("new project");
        projectEntity.setTotal(800.32);
        projectEntity.setOutgoing(0);
        projectEntity.setStart(LocalDate.now());
        projectEntity.setEnd(LocalDate.now().plusWeeks(10l));
        Set<UserEntity> users = new HashSet<>();
        users.add(userEntity1);
        projectEntity.setUsers(users);
        ProjectEntity projectEntity1 = projectRepository.save(projectEntity);

        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");
        categoryRepository.save(category);

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("0123456789");
        invoice.setDate(LocalDate.now());
        invoice.setValue(100);
        invoice.setProject(projectEntity1);
        invoiceRepository.save(invoice);

    }

    @AfterEach
    public void clean() {
        itemRepository.deleteAll();
    }

    @Test
    public void saveItem() {

        ItemEntity item = new ItemEntity();
        item.setName("pcr");
        item.setCategory(categoryRepository.findByName("Material permanente"));
        item.setQuantity(5);
        item.setValue(5.50);
        item.setInvoice(invoiceRepository.findByCode("0123456789"));
        ItemEntity itemEntity = itemRepository.save(item);

        assertTrue("pcr".equals(itemEntity.getName()));
    }

    @Test
    public void findItemReturnNull() {

        ItemEntity item = new ItemEntity();
        item.setName("pcr");
        item.setCategory(categoryRepository.findByName("Material permanente"));
        item.setQuantity(5);
        item.setValue(5.50);
        item.setInvoice(invoiceRepository.findByCode("0123456789"));

        assertFalse(itemRepository.findById(1l).isPresent());
    }

    @Test
    public void editItem() {

        ItemEntity item = new ItemEntity();
        item.setName("pcr");
        item.setCategory(categoryRepository.findByName("Material permanente"));
        item.setQuantity(5);
        item.setValue(5.50);
        item.setInvoice(invoiceRepository.findByCode("0123456789"));
        ItemEntity itemEntity = itemRepository.save(item);
        itemEntity.setName("pipet");
        itemRepository.save(itemEntity);

        Iterable<ItemEntity> all = itemRepository.findAll();
        List<ItemEntity> itemEntities = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger();
        all.forEach( it -> counter.getAndIncrement());
        all.forEach(it -> itemEntities.add(it));

        assertTrue("pipet".equals(itemEntities.get(0).getName()));
        assertEquals(1, counter.get());
    }

}
