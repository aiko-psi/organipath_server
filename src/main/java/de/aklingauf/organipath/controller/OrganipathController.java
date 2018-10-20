package de.aklingauf.organipath.controller;

import de.aklingauf.organipath.exception.ResourceNotFoundException;
import de.aklingauf.organipath.model.Task;
import de.aklingauf.organipath.repository.OrganipathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrganipathController {

    @Autowired
    OrganipathRepository organipathRepository;

    // Get All Notes
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return organipathRepository.findAll();
    }

    // / Create a new Note
    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return organipathRepository.save(task);
    }

    // Get a Single Note
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable(value = "id") Long taskId) {
        return organipathRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
    }

    // Update a Note
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable(value = "id") Long taskId,
                           @Valid @RequestBody Task taskDetails) {

        Task task = organipathRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        task.setName(taskDetails.getName());
        task.setNotes(taskDetails.getNotes());

        Task updatedTask = organipathRepository.save(task);
        return updatedTask;
    }


    // Delete a Note
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") Long taskId) {
        Task task = organipathRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", taskId));

        organipathRepository.delete(task);

        return ResponseEntity.ok().build();
    }

}
