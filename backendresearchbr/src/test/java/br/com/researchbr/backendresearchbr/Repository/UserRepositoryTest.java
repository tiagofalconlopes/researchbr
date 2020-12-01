package br.com.researchbr.backendresearchbr.Repository;

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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void setup() {

        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.ASSISTANT);
        RoleEntity roleEntity = roleRepository.save(roles);

    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void salveUser() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleRepository.findByName(RoleType.ASSISTANT));
        userRepository.save(userEntity);

        assertTrue("testuser".equals(userRepository.findByUsername("testuser").getUsername()));
    }

    @Test
    public void buscarUserRetornaNull() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleRepository.findByName(RoleType.ASSISTANT));

        assertNull(userRepository.findByUsername("testuser"));
    }

    @Test
    public void editUser() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleRepository.findByName(RoleType.ASSISTANT));
        UserEntity entity = userRepository.save(userEntity);
        entity.setPassword("asasas");

        Iterable<UserEntity> all = userRepository.findAll();
        AtomicInteger counter = new AtomicInteger();
        all.forEach( it -> counter.getAndIncrement());

        assertTrue("asasas".equals(userRepository.findByUsername("testuser").getPassword()));
        assertEquals(1, counter.get());
    }

    @Test
    public void deleteUser() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@testuser");
        userEntity.setPassword("testuser");
        userEntity.setCpf("01234567891");
        userEntity.setRole(roleRepository.findByName(RoleType.ASSISTANT));
        userRepository.save(userEntity);

        userRepository.delete(userEntity);

        Iterable<UserEntity> all = userRepository.findAll();
        AtomicInteger counter = new AtomicInteger();
        all.forEach( it -> counter.getAndIncrement());

        assertEquals(0, counter.get());
    }
}
