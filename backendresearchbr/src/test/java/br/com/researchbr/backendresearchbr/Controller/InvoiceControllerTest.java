package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.Entity.*;
import br.com.researchbr.backendresearchbr.Repository.*;
import br.com.researchbr.backendresearchbr.Service.Impl.ProjectServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    public void clean() {
        invoiceRepository.deleteAll();
    }

    @Test
    public void shouldReturn200WhenConsultingInvoice () throws  Exception {
        URI uri = new URI("/api/invoices/all");

        mockMvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .is(200)
                );
    }

    @Test
    @Transactional
    public void shouldSaveAndReturnAnInvoice() throws Exception {
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

        URI uri = new URI("/api/invoices/new");
        String json = "{\"code\": \"123456789\"," +
                "\"value\": 10.00," +
                "\"date\": \"2020-12-04\"," +
                "\"project\": {\"id\": " + projectEntity.getId() + "}}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .jsonPath("$.id").exists()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.code").value("123456789")
        );
    }

    @Test
    @Transactional
    public void shouldReturnAnInvoice() throws Exception {
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

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("123456789");
        invoice.setDate(LocalDate.now().plusDays(5l));
        invoice.setValue(10.00);
        invoice.setProject(projectEntity);
        InvoiceEntity invoiceEntity = invoiceRepository.save(invoice);


        URI uri = new URI("/api/invoices/invoice/" + invoiceEntity.getId());

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
    public void shouldEditAnInvoice() throws Exception {
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

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("123456789");
        invoice.setDate(LocalDate.now().plusDays(5l));
        invoice.setValue(10.00);
        invoice.setProject(projectEntity);
        InvoiceEntity invoiceEntity = invoiceRepository.save(invoice);

        URI uri = new URI("/api/invoices/invoice/edit/" + invoiceEntity.getId());

        String json = "{\"code\": \"123456789\"," +
                "\"value\": 15.00," +
                "\"date\": \"2020-12-08\"," +
                "\"project\": {\"id\": " + projectEntity.getId() + "}}";

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
    public void shouldDeleteAnInvoice() throws Exception {
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

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("123456789");
        invoice.setDate(LocalDate.now().plusDays(5l));
        invoice.setValue(10.00);
        invoice.setProject(projectEntity);
        InvoiceEntity invoiceEntity = invoiceRepository.save(invoice);
        URI uri = new URI("/api/invoices/invoice/delete/" + invoiceEntity.getId());
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
