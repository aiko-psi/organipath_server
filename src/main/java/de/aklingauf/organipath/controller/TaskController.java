package de.aklingauf.organipath.controller;

import de.aklingauf.organipath.checks.AuthChecker;
import de.aklingauf.organipath.exception.ResourceNotFoundException;
import de.aklingauf.organipath.model.Task;
import de.aklingauf.organipath.model.User;
import de.aklingauf.organipath.repository.TaskRepository;
import de.aklingauf.organipath.repository.ProjRepository;
import de.aklingauf.organipath.repository.TaskSpecifications;
import de.aklingauf.organipath.security.CurrentUser;
import de.aklingauf.organipath.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjRepository projectRepository;

    @Autowired
    AuthChecker authChecker;

    // Get All Tasks that belong to one Project
    @GetMapping("/projects/{projectId}/alltasks")
    public List<Task> getAllTasks(@CurrentUser UserPrincipal currentUser,
                                  @PathVariable (value = "projectId") Long projectId) {

        authChecker.checkProject(projectId, currentUser);

        return taskRepository.findByProjectId(projectId);
    }

    //Get all direct children tasks of one project
    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> getProjectTasks(@CurrentUser UserPrincipal currentUser,
                                      @PathVariable (value = "projectId") Long projectId) {

        authChecker.checkProject(projectId, currentUser);

        return taskRepository.findAll(TaskSpecifications.isDirectChildOf(0L)
                .and(TaskSpecifications.isInProject(projectId)));
    }

    // Get All direct Subtasks of one Task
    @GetMapping("/projects/{projectId}/tasks/{taskId}/subtasks")
    public List<Task> getAllSubtasks(@CurrentUser UserPrincipal currentUser,
                                     @PathVariable (value = "projectId") Long projectId,
                                     @PathVariable(value = "taskId") Long taskId) {

        authChecker.checkTaskAndProject(taskId,projectId,currentUser);

        return taskRepository.findAll(TaskSpecifications.isDirectChildOf(taskId)
                .and(TaskSpecifications.isInProject(projectId)));
    }

    // Get a Single Task (not in the tutorial)
    @GetMapping("projects/{projectId}/tasks/{taskId}")
    public Task getTaskById(@CurrentUser UserPrincipal currentUser,
                            @PathVariable (value = "projectId") Long projectId,
                            @PathVariable (value = "taskId") Long taskId) {

        authChecker.checkTaskAndProject(taskId,projectId,currentUser);

        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
    }

    // / Create a new Task
    @PostMapping("/projects/{projectId}/tasks")
    public Task createTask(@CurrentUser UserPrincipal currentUser,
                           @PathVariable (value = "projectId") Long projectId,
                           @Valid @RequestBody Task task) {

        authChecker.checkProject(projectId, currentUser);

        return projectRepository.findById(projectId).map(project -> {
            task.setProject(project);
            task.setProjectId(projectId);
            this.setTaskParent(task.getParentId(), task);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
    }

    // Update a Task
    @PutMapping("projects/{projectId}/tasks/{taskId}")
    public Task updateTask(@CurrentUser UserPrincipal currentUser,
                           @PathVariable (value = "projectId") Long projectId,
                           @PathVariable (value = "taskId") Long taskId,
                           @Valid @RequestBody Task taskRequest) {

        authChecker.checkTaskAndProject(taskId, projectId, currentUser);

        return taskRepository.findById(taskId).map(task -> {
            task.setName(taskRequest.getName());
            task.setNotes(taskRequest.getNotes());
            task.setProjectId(projectId);
            this.setTaskParent(task.getParentId(), task);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
    }


    // Delete a Task
    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@CurrentUser UserPrincipal currentUser,
                                        @PathVariable(value = "projectId") Long projectId,
                                        @PathVariable(value = "taskId") Long taskId) {

        authChecker.checkTaskAndProject(taskId, projectId, currentUser);

        return taskRepository.findById(taskId).map(task -> {
            taskRepository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
    }


    private void setTaskParent(Long parentId, Task task){
        if (parentId == 0){
            task.setParent(null);
        }else {
            taskRepository.findById(parentId).map(parentTask -> {
                task.setParent(parentTask);
                return task;
            }).orElseThrow(() -> new ResourceNotFoundException("Task", "ParentId", parentId));
        }
    }




}
