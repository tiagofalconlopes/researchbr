package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleType;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import br.com.researchbr.backendresearchbr.Repository.RoleRepository;
import br.com.researchbr.backendresearchbr.Repository.UserRepository;
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
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        RoleEntity role = new RoleEntity();
        role.setName(RoleType.PRINCIPAL);
        roleRepository.save(role);
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturn200WhenConsultingUser () throws  Exception {
        URI uri = new URI("/api/users/all");

        mockMvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .is(200)
                );
    }

    @Test
    public void shouldSaveAndReturnAnUser() throws Exception {
        List<RoleEntity> roleEntity = (List<RoleEntity>) roleRepository.findAll();
        RoleType roleName = roleEntity.get(0).getName();

        URI uri = new URI("/api/users/new");
        String json = "{\"username\": \"username\"," +
                "\"email\": \"email@email\"," +
                "\"password\": \"password\"," +
                "\"cpf\": \"01234567891\"," +
                "\"roleName\": \"" + roleName + "\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .jsonPath("$.result.id").exists()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.result.username").value("username")
        );
    }

    @Test
    @Transactional
    public void shouldReturnAnUserByUsername() throws Exception {
        List<RoleEntity> roleEntity = (List<RoleEntity>) roleRepository.findAll();
        RoleEntity role = roleEntity.get(0);

        UserEntity userEntity = new UserEntity();
        userEntity.setRole(role);
        userEntity.setPassword("password");
        userEntity.setUsername("username");
        userEntity.setCpf("0123456789");
        userEntity.setEmail("email@email.com");

        UserEntity theUser = userRepository.save(userEntity);

        URI uri = new URI("/api/users/user/" + theUser.getUsername());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.result.username")
                .value("username")
        );
    }

    @Test
    @Transactional
    public void shouldReturnEmails() throws Exception {
        List<RoleEntity> roleEntity = (List<RoleEntity>) roleRepository.findAll();
        RoleEntity role = roleEntity.get(0);

        UserEntity userEntity = new UserEntity();
        userEntity.setRole(role);
        userEntity.setPassword("password");
        userEntity.setUsername("username");
        userEntity.setCpf("0123456789");
        userEntity.setEmail("email@email.com");

        UserEntity theUser = userRepository.save(userEntity);

        URI uri = new URI("/api/users/userEmail");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );

        List<String> emails = userService.findAllEmail();
        Assert.assertTrue(emails.size() > 0);
    }

    @Test
    @Transactional
    public void shouldEditAnUser() throws Exception {
        List<RoleEntity> roleEntity = (List<RoleEntity>) roleRepository.findAll();
        RoleEntity role = roleEntity.get(0);
        String roleName = roleEntity.get(0).getName().toString();
        for (int i = 1; i < roleEntity.size(); i++) {
            roleRepository.delete(roleEntity.get(i));
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setRole(role);
        userEntity.setPassword("password");
        userEntity.setUsername("username");
        userEntity.setCpf("0123456789");
        userEntity.setEmail("email@email.com");

        UserEntity theUser = userRepository.save(userEntity);

        URI uri = new URI("/api/users/user/edit/" + theUser.getId());

        String json = "{\"username\": \"newusername\"," +
                "\"email\": \"email@email\"," +
                "\"password\": \"password\"," +
                "\"roleName\": \"" + roleName +"\"," +
                "\"cpf\": \"01234567891\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        ).andExpect(MockMvcResultMatchers
                .jsonPath("$.result.username")
                .value("newusername")
        );
    }

    @Test
    @Transactional
    public void shouldDeleteAnUser() throws Exception {
        List<RoleEntity> roleEntity = (List<RoleEntity>) roleRepository.findAll();
        RoleEntity role = roleEntity.get(0);

        UserEntity userEntity = new UserEntity();
        userEntity.setRole(role);
        userEntity.setPassword("password");
        userEntity.setUsername("username");
        userEntity.setCpf("0123456789");
        userEntity.setEmail("email@email.com");

        UserEntity theUser = userRepository.save(userEntity);
        URI uri = new URI("/api/users/user/delete/" + theUser.getId());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers
                .status()
                .isOk()
        );
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        Assertions.assertTrue(userEntities.isEmpty());
    }
}
