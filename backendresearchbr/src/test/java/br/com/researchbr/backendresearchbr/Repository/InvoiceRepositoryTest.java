package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

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
        UserEntity entity = userRepository.save(userEntity);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCode("123456/2020");
        projectEntity.setAgency("CNPq");
        projectEntity.setTitle("new project");
        projectEntity.setTotal(800.32);
        projectEntity.setOutgoing(0);
        projectEntity.setStart(LocalDate.now());
        projectEntity.setEnd(LocalDate.now().plusWeeks(10l));
        Set<UserEntity> users = new HashSet<>();
        users.add(userRepository.findByUsername("testuser"));
        projectEntity.setUsers(users);
        ProjectEntity projectEntity1 = projectRepository.save(projectEntity);

    }

    @AfterEach
    public void clean() {
        invoiceRepository.deleteAll();
    }

    @Test
    public void saveInvoice() {

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("0123456789");
        invoice.setDate(LocalDate.now());
        invoice.setValue(300);
        invoice.setProject(projectRepository.findByCode("123456/2020"));
        invoiceRepository.save(invoice);

        assertTrue(invoice.getCode().equals(invoiceRepository.findByCode("0123456789").getCode()));
    }

    @Test
    public void findByInvoiceCode() {

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("0123456789");
        invoice.setValue(300);
        invoice.setDate(LocalDate.now());
        invoice.setProject(projectRepository.findByCode("123456/2020"));
        assertNull(invoiceRepository.findByCode("0123456789"));
    }

    @Test
    public void editInvoice() {

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCode("0123456789");
        invoice.setValue(300);
        invoice.setDate(LocalDate.now());
        invoice.setProject(projectRepository.findByCode("123456/2020"));
        InvoiceEntity newInvoice = invoiceRepository.save(invoice);
        newInvoice.setCode("0101010101");
        invoiceRepository.save(newInvoice);

        assertNull(invoiceRepository.findByCode("0123456789"));
        assertTrue("0101010101".equals(invoiceRepository.findByCode("0101010101").getCode()));
    }

}
