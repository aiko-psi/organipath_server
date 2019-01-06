package de.aklingauf.organipath.controller;

import de.aklingauf.organipath.exception.ResourceNotFoundException;
import de.aklingauf.organipath.model.Task;
import de.aklingauf.organipath.repository.TaskRepository;
import de.aklingauf.organipath.repository.ProjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjRepository projectRepository;

    // Get All Notes
    @GetMapping("/projects/{projectId}/tasks")
    public Page<Task> getAllTasks(@PathVariable (value = "projectId") Long projectId,
                                  Pageable pageable) {
        return taskRepository.findByProjectId(projectId, pageable);
    }

    // / Create a new Note
    @PostMapping("/projects/{projectId}/tasks")
    public Task createTask(@PathVariable (value = "projectId") Long projectId, @Valid @RequestBody Task task) {
        return projectRepository.findById(projectId).map(project -> {
            task.setProject(project);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
    }

    // Get a Single Note (not in the tutorial)
    @GetMapping("projects/{projectId}/tasks/{taskId}")
    public Task getTaskById(@PathVariable (value = "projectId") Long projectId,
                            @PathVariable (value = "taskId") Long taskId,
                            Pageable pageable) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "projectId", projectId);
        }

        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
    }

    @PutMapping("projects/{projectId}/tasks/{taskId}")
    public Task updateTask(@PathVariable (value = "projectId") Long projectId,
                           @PathVariable (value = "taskId") Long taskId,
                                 @Valid @RequestBody Task taskRequest) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }

        return taskRepository.findById(taskId).map(task -> {
            task.setName(taskRequest.getName());
            task.setNotes(taskRequest.getNotes());
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
    }


    // Delete a Note
    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "projectId") Long projectId,
                                        @PathVariable(value = "taskId") Long taskId) {

        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "projectId", projectId);
        }

        return taskRepository.findById(taskId).map(task -> {
            taskRepository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
    }


}
