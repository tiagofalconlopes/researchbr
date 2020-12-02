package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleType;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProjectRepositoryTest {

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

    }

    @AfterEach
    public void clean() {
        projectRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void saveProject() {

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

        assertTrue("CNPq".equals(projectEntity1.getAgency()));
    }

    @Test
    public void findProjectReturnNull() {

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

        assertNull(projectRepository.findByCode("123456/2020"));
    }

    @Test
    public void editProject() {

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
        projectEntity1.setTotal(900.00);
        projectRepository.save(projectEntity1);

        Iterable<ProjectEntity> all = projectRepository.findAll();
        AtomicInteger counter = new AtomicInteger();
        all.forEach( it -> counter.getAndIncrement());

        assertEquals(900.00, projectRepository.findByCode("123456/2020").getTotal(), 0.000001);
        assertEquals(1, counter.get());
    }

    @Test
    public void deleteProject() {

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

        projectRepository.delete(projectEntity1);

        Iterable<ProjectEntity> all = projectRepository.findAll();
        AtomicInteger counter = new AtomicInteger();
        all.forEach( it -> counter.getAndIncrement());

        assertEquals(0, counter.get());
    }
}
