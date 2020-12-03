package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleType;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import br.com.researchbr.backendresearchbr.Repository.ProjectRepository;
import br.com.researchbr.backendresearchbr.Repository.RoleRepository;
import br.com.researchbr.backendresearchbr.Repository.UserRepository;
import br.com.researchbr.backendresearchbr.Service.Impl.ProjectServiceImpl;
import br.com.researchbr.backendresearchbr.Service.Impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectServiceImpl projectService;

    @AfterEach
    public void clean() {
        projectRepository.deleteAll();
    }

    @Test
    public void shouldReturn200WhenConsultingProject () throws  Exception {
        URI uri = new URI("/api/projects/all");

        mockMvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .is(200)
                );
    }

    @Test
    @Transactional
    public void shouldSaveAndReturnAProject() throws Exception {
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

        URI uri = new URI("/api/projects/new");
        String json = "{\"code\": \"123456/2020\"," +
                "\"agency\": \"CNPq\"," +
                "\"title\": \"new title\"," +
                "\"total\": 900.00," +
                "\"outgoing\": 0.00," +
                "\"start\": \"2020-12-10\"," +
                "\"end\": \"2021-01-15\"," +
                "\"usersIds\": [" + entity.getId() + "]}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .jsonPath("$.id").exists()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.code").value("123456/2020")
        );
    }

    @Test
    @Transactional
    public void shouldReturnAProject() throws Exception {
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

        URI uri = new URI("/api/projects/project/" + projectEntity.getId());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.total")
                .value(900.00)
        );
    }

    @Test
    @Transactional
    public void shouldEditAProject() throws Exception {
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

        URI uri = new URI("/api/projects/project/edit/" + projectEntity.getId());

        String json = "{\"code\": \"123456/2020\"," +
                "\"agency\": \"CAPES\"," +
                "\"title\": \"new title\"," +
                "\"total\": 900.00," +
                "\"outgoing\": 0.00," +
                "\"start\": \"2020-12-10\"," +
                "\"end\": \"2021-01-15\"," +
                "\"usersIds\": [" + entity.getId() + "]}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.agency")
                .value("CAPES")
        );
    }

    @Test
    @Transactional
    public void shouldDeleteAProject() throws Exception {
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
        URI uri = new URI("/api/projects/project/delete/" + projectEntity.getId());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );
        List<ProjectEntity> projectEntities = (List<ProjectEntity>) projectRepository.findAll();
        Assertions.assertTrue(projectEntities.isEmpty());
    }
}
