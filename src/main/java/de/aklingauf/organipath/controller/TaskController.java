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

    // Get All Tasks of one Project
    @GetMapping("/projects/{projectId}/tasks")
    public Page<Task> getAllTasks(@PathVariable (value = "projectId") Long projectId,
                                  Pageable pageable) {
        return taskRepository.findByProjectId(projectId, pageable);
    }

    // / Create a new Task
    @PostMapping("/projects/{projectId}/parent/{parentId}/tasks")
    public Task createTask(@PathVariable (value = "projectId") Long projectId,
                           @PathVariable (value = "parentId") Long parentId,
                           @Valid @RequestBody Task task) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }

        if(parentId!= 0 && !taskRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("Task", "id", parentId);
        }

        return projectRepository.findById(projectId).map(project -> {
            task.setProject(project);
            this.setParent(parentId, task);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
    }

    // Get a Single Task (not in the tutorial)
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

    // Update a Task
    // if there is the Param "parentId" change this too, else leave the parent alone
    @PutMapping("projects/{projectId}/parent/{parentId}/tasks/{taskId}")
    public Task updateTask(@PathVariable (value = "projectId") Long projectId,
                           @PathVariable (value = "taskId") Long taskId,
                           @PathVariable (value = "parentId") Long parentId,
                           @Valid @RequestBody Task taskRequest) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }

        if(parentId!= 0 && !taskRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("Task", "id", parentId);
        }

        return taskRepository.findById(taskId).map(task -> {
            task.setName(taskRequest.getName());
            task.setNotes(taskRequest.getNotes());
            this.setParent(parentId, task);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
    }


    // Delete a Task
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

    // Get All Subtasks
    @GetMapping("/projects/{projectId}/parent/{taskId}/subtasks")
    public Page<Task> getAllSubtasks(@PathVariable (value = "projectId") Long projectId,
                                     @PathVariable(value = "taskId") Long taskId,
                                     Pageable pageable) {
        return taskRepository.findByParentId(taskId, pageable);
    }

    // / Create a new Subtask
    @PostMapping("/projects/{projectId}/parent/{taskId}/subtasks")
    public Task createSubtask(@PathVariable (value = "projectId") Long projectId,
                              @PathVariable(value = "taskId") Long taskId,
                              @Valid @RequestBody Task task) {
        return taskRepository.findById(taskId).map(parent -> {
            task.setProject(parent.getProject()); //add test here!
            task.setParent(parent);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
    }


    public Task setParent(Long parentId, Task task){
        if (parentId == 0){
            task.setParent(null);
            return task;
        }else {
            taskRepository.findById(parentId).map(parentTask -> {
                task.setParent(parentTask);
                return task;
            }).orElseThrow(() -> new ResourceNotFoundException("Task", "ParentId", parentId));
            return task;
        }
    }




}
