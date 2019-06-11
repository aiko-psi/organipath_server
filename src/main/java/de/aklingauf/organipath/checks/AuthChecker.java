package de.aklingauf.organipath.checks;

import de.aklingauf.organipath.exception.BadRequestException;
import de.aklingauf.organipath.exception.ResourceNotFoundException;
import de.aklingauf.organipath.security.UserPrincipal;
import de.aklingauf.organipath.repository.TaskRepository;
import de.aklingauf.organipath.repository.ProjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthChecker {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjRepository projectRepository;

    public void checkProject(Long projectId, UserPrincipal currentUser){
        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFoundException("Project", "id", projectId);
        }

        projectRepository.findById(projectId).map(project -> {
            if(!currentUser.getId().equals(project.getOwnerId())){
                throw new BadRequestException("Project is not owned by user");
            }
            return project;
        });
    }

    public void checkTaskAndProject(Long projectId, Long taskId, UserPrincipal currentUser){
        if(!taskRepository.existsById(taskId)){
            throw new ResourceNotFoundException("Task", "id", taskId);
        }

        taskRepository.findById(taskId).map(task -> {
            if(!task.getProjectId().equals(projectId)){
                throw new BadRequestException("Task does not belong to Project");
            }
            return task;
        });

        this.checkProject(projectId, currentUser);
    }
}
