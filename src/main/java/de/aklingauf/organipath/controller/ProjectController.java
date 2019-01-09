package de.aklingauf.organipath.controller;

import de.aklingauf.organipath.checks.AuthChecker;
import de.aklingauf.organipath.exception.ResourceNotFoundException;
import de.aklingauf.organipath.model.Project;
import de.aklingauf.organipath.repository.ProjRepository;
import de.aklingauf.organipath.repository.ProjectSpecifications;
import de.aklingauf.organipath.repository.UserRepository;
import de.aklingauf.organipath.security.CurrentUser;
import de.aklingauf.organipath.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    AuthChecker authChecker;

    @GetMapping("/projects")
    public List<Project> getAllProjects(@CurrentUser UserPrincipal currentUser,
                                        Pageable pagale){
        return projectRepository.findAll(ProjectSpecifications.isOwnedBy(currentUser));
    }

    @GetMapping("/projects/{projectId}")
    public Project getProjectById(@CurrentUser UserPrincipal currentUser,
                                  @PathVariable(value = "projectId") Long projectId) {

        authChecker.checkProject(projectId, currentUser);

        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
    }

    @PostMapping("/projects")
    public Project createProject(@CurrentUser UserPrincipal currentUser,
                                 @Valid @RequestBody Project project) {
        userRepository.findById(currentUser.getId()).map(user -> {
            project.setOwner(user);
            project.setOwnerId(user.getId());
            return user;
        });
        return projectRepository.save(project);
    }

    @PutMapping("/projects/{projectId}")
    public Project updateProject(@CurrentUser UserPrincipal currentUser,
                                 @PathVariable Long projectId,
                                 @Valid @RequestBody Project projectRequest) {

        authChecker.checkProject(projectId, currentUser);

        return projectRepository.findById(projectId).map(project -> {
            project.setName(projectRequest.getName());
            project.setPrivacy(projectRequest.getPrivacy());
            return projectRepository.save(project);
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "projectId", projectId));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@CurrentUser UserPrincipal currentUser,
                                           @PathVariable Long projectId) {
        
        authChecker.checkProject(projectId, currentUser);

        return projectRepository.findById(projectId).map(project -> {
            projectRepository.delete(project);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "id", projectId));
    }

}
