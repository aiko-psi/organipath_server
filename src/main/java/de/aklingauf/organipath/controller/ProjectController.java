package de.aklingauf.organipath.controller;

import de.aklingauf.organipath.exception.ResourceNotFoundException;
import de.aklingauf.organipath.model.Project;
import de.aklingauf.organipath.repository.ProjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjRepository projectRepository;

    @GetMapping("/projects")
    public Page<Project> getAllProjects(Pageable pagale){
        return projectRepository.findAll(pagale);
    }

    // no paging here (other tutorial), do I need it?
    @GetMapping("/projects/{projectId}")
    public Project getProjectById(@PathVariable(value = "projectId") Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
    }

    @PostMapping("/projects")
    public Project createProject(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }

    @PutMapping("/projects/{projectId}")
    public Project updateProject(@PathVariable Long projectId, @Valid @RequestBody Project projectRequest) {
        return projectRepository.findById(projectId).map(project -> {
            project.setName(projectRequest.getName());
            project.setPrivacy(projectRequest.getPrivacy());
            return projectRepository.save(project);
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "projectId", projectId));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        return projectRepository.findById(projectId).map(project -> {
            projectRepository.delete(project);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "id", projectId));
    }


}
