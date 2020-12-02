package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.DTO.ProjectDto;
import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import br.com.researchbr.backendresearchbr.Service.Impl.ProjectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/projects")
public class ProjectController {

    @Autowired
    ProjectServiceImpl projectService;

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);




    @GetMapping(value = "/all")
    public List<ProjectDto> listAll(){
        log.info("Finding projects.");
        log.warn("The list can return null if no projects are available!");
        List<ProjectDto> projectDtos = new ArrayList<>();
        projectService.all().forEach( project -> projectDtos.add(new ProjectDto(project)));
        return projectDtos;
    }

    @PostMapping(value = "/new")
    public ProjectDto create(@RequestBody ProjectDto projectDto){
        log.info("Creating a new project.");
        ProjectDto newProjectDto = projectService.saveWithUsersIds(projectDto);
        return newProjectDto;
    }

    @GetMapping(value = "/project/{id}")
    public ProjectDto findAProject(@PathVariable Long id){
        log.info("finding project of id " + id);
        return new ProjectDto(projectService.byId(id));
    }

    @PutMapping(value = "/project/edit/{id}")
    public ProjectDto editAProject(@PathVariable(value = "id") Long id, @RequestBody ProjectDto projectDto){
        log.info("Editing project of id: " + id);
        ProjectEntity projectEntity = projectDto.convert();
        ProjectDto newDTO = new ProjectDto(projectService.edit(projectEntity, id));
        return newDTO;
    }


    @DeleteMapping(value = "/project/delete/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        log.info("Deleting project of id: " + id);
        projectService.deleteById(id);
    }

}
