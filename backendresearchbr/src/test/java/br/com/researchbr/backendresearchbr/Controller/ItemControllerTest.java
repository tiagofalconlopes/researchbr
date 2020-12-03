package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.Entity.*;
import br.com.researchbr.backendresearchbr.Repository.*;
import br.com.researchbr.backendresearchbr.Service.Impl.ProjectServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ItemRepository itemRepository;


    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void shouldReturn200WhenConsultingItem () throws  Exception {
        URI uri = new URI("/api/items/all");

        mockMvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .is(200)
                );
    }

    @Test
    @Transactional
    public void shouldSaveAndReturnAnItem() throws Exception {
        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.PRINCIPAL);
        RoleEntity roleEntity = roleRepository.save(roles);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleEntity);
        UserEntity entity = userRepository.save(userEntity);

        Set<UserEntity> users = new HashSet<>();
        users.add(entity);

        ProjectEntity project = new ProjectEntity();
        project.setCode("123456/2020");
        project.setTitle("new title");
        project.setAgency("CNPq");
        project.setStart(LocalDate.now().plusDays(3l));
        project.setEnd(LocalDate.now().plusWeeks(5l));
        project.setTotal(900.00);
        project.setOutgoing(0.00);
        project.setUsers(users);
        ProjectEntity projectEntity = projectRepository.save(project);

        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");
        CategoryEntity newCategoryEntity = categoryRepository.save(category);

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("123456789");
        invoice.setDate(LocalDate.now().plusDays(5l));
        invoice.setValue(10.00);
        invoice.setProject(projectEntity);
        InvoiceEntity invoiceEntity = invoiceRepository.save(invoice);

        URI uri = new URI("/api/items/new");
        String json = "{\"name\": \"pcr\"," +
                "\"value\": 10.00," +
                "\"quantity\": 2," +
                "\"description\": \"new item\"," +
                "\"invoice\": {\"id\": " + invoiceEntity.getId() + "}," +
                "\"category\": {\"name\": \"" + newCategoryEntity.getName() + "\"}}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .jsonPath("$.id").exists()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.value").value(10.00)
        );
    }

    @Test
    @Transactional
    public void shouldReturnAnItem() throws Exception {
        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.PRINCIPAL);
        RoleEntity roleEntity = roleRepository.save(roles);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleEntity);
        UserEntity entity = userRepository.save(userEntity);

        Set<UserEntity> users = new HashSet<>();
        users.add(entity);

        ProjectEntity project = new ProjectEntity();
        project.setCode("123456/2020");
        project.setTitle("new title");
        project.setAgency("CNPq");
        project.setStart(LocalDate.now().plusDays(3l));
        project.setEnd(LocalDate.now().plusWeeks(5l));
        project.setTotal(900.00);
        project.setOutgoing(0.00);
        project.setUsers(users);
        ProjectEntity projectEntity = projectRepository.save(project);

        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");
        CategoryEntity newCategoryEntity = categoryRepository.save(category);

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("123456789");
        invoice.setDate(LocalDate.now().plusDays(5l));
        invoice.setValue(10.00);
        invoice.setProject(projectEntity);
        InvoiceEntity invoiceEntity = invoiceRepository.save(invoice);

        ItemEntity item = new ItemEntity();
        item.setName("pcr");
        item.setValue(10.00);
        item.setQuantity(2);
        item.setDescription("new item");
        item.setInvoice(invoiceEntity);
        item.setCategory(newCategoryEntity);
        ItemEntity itemEntity = itemRepository.save(item);


        URI uri = new URI("/api/items/item/" + itemEntity.getId());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.value")
                .value(10.00)
        );
    }

    @Test
    @Transactional
    public void shouldEditAnItem() throws Exception {
        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.PRINCIPAL);
        RoleEntity roleEntity = roleRepository.save(roles);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleEntity);
        UserEntity entity = userRepository.save(userEntity);

        Set<UserEntity> users = new HashSet<>();
        users.add(entity);

        ProjectEntity project = new ProjectEntity();
        project.setCode("123456/2020");
        project.setTitle("new title");
        project.setAgency("CNPq");
        project.setStart(LocalDate.now().plusDays(3l));
        project.setEnd(LocalDate.now().plusWeeks(5l));
        project.setTotal(900.00);
        project.setOutgoing(0.00);
        project.setUsers(users);
        ProjectEntity projectEntity = projectRepository.save(project);

        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");
        CategoryEntity newCategoryEntity = categoryRepository.save(category);

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("123456789");
        invoice.setDate(LocalDate.now().plusDays(5l));
        invoice.setValue(10.00);
        invoice.setProject(projectEntity);
        InvoiceEntity invoiceEntity = invoiceRepository.save(invoice);

        ItemEntity item = new ItemEntity();
        item.setName("pcr");
        item.setValue(10.00);
        item.setQuantity(2);
        item.setDescription("new item");
        item.setInvoice(invoiceEntity);
        item.setCategory(newCategoryEntity);
        ItemEntity itemEntity = itemRepository.save(item);

        URI uri = new URI("/api/items/item/edit/" + itemEntity.getId());

        String json = "{\"name\": \"pcr\"," +
                "\"value\": 15.00," +
                "\"quantity\": 2," +
                "\"description\": \"new item\"," +
                "\"invoice\": {\"id\": " + invoiceEntity.getId() + "}," +
                "\"category\": {\"name\": \"" + newCategoryEntity.getName() + "\"}}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.value")
                .value(15.00)
        );
    }

    @Test
    @Transactional
    public void shouldDeleteAnItem() throws Exception {
        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.PRINCIPAL);
        RoleEntity roleEntity = roleRepository.save(roles);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleEntity);
        UserEntity entity = userRepository.save(userEntity);

        Set<UserEntity> users = new HashSet<>();
        users.add(entity);

        ProjectEntity project = new ProjectEntity();
        project.setCode("123456/2020");
        project.setTitle("new title");
        project.setAgency("CNPq");
        project.setStart(LocalDate.now().plusDays(3l));
        project.setEnd(LocalDate.now().plusWeeks(5l));
        project.setTotal(900.00);
        project.setOutgoing(0.00);
        project.setUsers(users);
        ProjectEntity projectEntity = projectRepository.save(project);

        CategoryEntity category = new CategoryEntity();
        category.setName("Material permanente");
        CategoryEntity newCategoryEntity = categoryRepository.save(category);

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("123456789");
        invoice.setDate(LocalDate.now().plusDays(5l));
        invoice.setValue(10.00);
        invoice.setProject(projectEntity);
        InvoiceEntity invoiceEntity = invoiceRepository.save(invoice);

        ItemEntity item = new ItemEntity();
        item.setName("pcr");
        item.setValue(10.00);
        item.setQuantity(2);
        item.setDescription("new item");
        item.setInvoice(invoiceEntity);
        item.setCategory(newCategoryEntity);
        ItemEntity itemEntity = itemRepository.save(item);
        URI uri = new URI("/api/items/item/delete/" + itemEntity.getId());
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );

    }
}
