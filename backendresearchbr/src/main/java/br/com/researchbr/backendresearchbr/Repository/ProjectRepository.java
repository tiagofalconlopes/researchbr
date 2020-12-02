package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
    ProjectEntity findByCode(String code);
    ProjectEntity findByTitle(String title);
    List<ProjectEntity> findByAgency(String agency);
}
