package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    @Query(value = "SELECT * FROM role where name IN (:role)", nativeQuery = true)
    RoleEntity find(@Param("role") String role);

    RoleEntity findByName(RoleType roleType);
}
