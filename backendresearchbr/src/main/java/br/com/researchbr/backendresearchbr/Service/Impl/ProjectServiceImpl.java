package br.com.researchbr.backendresearchbr.Service.Impl;

import br.com.researchbr.backendresearchbr.DTO.ProjectDto;
import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import br.com.researchbr.backendresearchbr.Repository.ProjectRepository;
import br.com.researchbr.backendresearchbr.Repository.UserRepository;
import br.com.researchbr.backendresearchbr.Service.ServiceAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class ProjectServiceImpl extends ServiceAbstract<ProjectRepository, ProjectEntity, Long> {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public ProjectDto saveWithUsersIds(ProjectDto projectDto) {
        logger.warn("If fields are missing it will not be possible to save the entity");
        try{
            testProjectDuplicatedData(projectDto);
            ProjectEntity projectEntity = createProjectEntity(projectDto);
            return new ProjectDto(projectRepository.save(projectEntity));
        } catch (Exception e) {
            logger.error("Error while saving entity: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private void testProjectDuplicatedData(ProjectDto projectDto) {
        ProjectEntity projectWithDuplicatedCode = projectRepository.findByCode(projectDto.getCode());
        if(projectWithDuplicatedCode != null) {
            log.error(String.format("Duplicated code %", projectDto.getCode()));
            throw new RuntimeException("Duplicated code.");
        }

        ProjectEntity projectWithDuplicatedTitle = projectRepository.findByTitle(projectDto.getTitle());
        if(projectWithDuplicatedTitle != null) {
            log.error(String.format("Duplicated title %", projectDto.getCode()));
            throw new RuntimeException("Duplicated title.");
        }
    }

    private ProjectEntity createProjectEntity(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCode(projectDto.getCode());
        projectEntity.setAgency((projectDto.getAgency()));
        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setTotal(projectDto.getTotal());
        projectEntity.setOutgoing(projectDto.getOutgoing());
        projectEntity.setStart(projectDto.getStart());
        projectEntity.setEnd(projectDto.getEnd());
        Set<UserEntity> users = new HashSet<>();
        projectDto.getUsersIds().forEach( id -> users.add(userRepository.findById(id).get()) );
        projectEntity.setUsers(users);
        return projectEntity;
    }
}
