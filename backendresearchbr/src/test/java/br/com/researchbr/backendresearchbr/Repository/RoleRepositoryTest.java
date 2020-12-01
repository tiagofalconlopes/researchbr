package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {

    @Autowired
    RoleRepository repository;

    @BeforeEach
    public void clean() {
        repository.deleteAll();
    }

    @Test
    public void salveRole() {

        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.ASSISTANT);
        repository.save(roles);

        assertEquals(roles.getName(), repository.findByName(RoleType.ASSISTANT).getName());
    }

    @Test
    public void findByRoleName() {

        RoleType roleName = RoleType.ASSISTANT;
        assertNull(repository.findByName(RoleType.ASSISTANT));
    }

    @Test
    public void findByRoleDescription() {
        String roleDescription = "Role_PRINCIPAL";
        assertNull(repository.find(roleDescription));
    }

    @Test
    public void editRole() {

        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.ASSISTANT);
        RoleEntity newRole = repository.save(roles);
        newRole.setName(RoleType.PRINCIPAL);
        repository.save(newRole);

        RoleType roleName = RoleType.ASSISTANT;
        RoleType newRoleName = RoleType.PRINCIPAL;
        assertNull(repository.findByName(roleName));
        assertEquals(newRole.getName(), repository.findByName(newRoleName).getName());
    }

    @Test
    public void deleteRole() {

        RoleEntity roles = new RoleEntity();
        roles.setName(RoleType.ASSISTANT);
        RoleType roleName = RoleType.ASSISTANT;
        RoleEntity newRole = repository.save(roles);

        repository.delete(newRole);

        assertNull(repository.findByName(roleName));
    }
}
