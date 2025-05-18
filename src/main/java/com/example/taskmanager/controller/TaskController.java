package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "'")
public class TaskController {
	
	private final TaskRepository repo;
	
	public TaskController(TaskRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping
	public List<Task> getAllTasks() {
		return repo.findAll();
	}
	
	@PostMapping
	public Task createTask(@RequestBody Task task) {
		return repo.save(task);
	}
	
	@PutMapping("/{id}")
	public Task updateTask(@PathVariable Long id, @RequestBody Task updated) {
		return repo.findById(id).map(task -> {
			task.setTitle(updated.getTitle());
			task.setDescription(updated.getDescription());
			task.setCompleted(updated.isCompleted());
			return repo.save(task);
		}).orElseThrow();
	}
	
	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		repo.deleteById(id);
	}

}
