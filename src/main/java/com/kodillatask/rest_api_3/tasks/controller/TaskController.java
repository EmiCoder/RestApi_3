package com.kodillatask.rest_api_3.tasks.controller;

import com.kodillatask.rest_api_3.tasks.domain.task.TaskDto;
import com.kodillatask.rest_api_3.tasks.mapper.TaskMapper;
import com.kodillatask.rest_api_3.tasks.service.TaskService;
import javassist.NotFoundException;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @Autowired
    private TaskService service;
    @Autowired
    private TaskMapper mapper;


    @GetMapping
    public ResponseEntity getTasks() throws NotFoundException {
        List<TaskDto> list = mapper.mapToTaskDtoList(service.getAllTasks());
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(list);
        } else {
            return ResponseEntity.badRequest().body("List not found");

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        if (!service.getAllTasks().stream().anyMatch(task -> task.getId().equals(id))) {
            return ResponseEntity.badRequest().body("Task with given id does not exists.");
        }
        return new ResponseEntity<>(mapper.mapToTaskDto(service.findById(id).get()), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(
                "resr_api_3",
                false,
                "task",
                id.toString()))
                .build();
    }



    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody TaskDto taskDto) {
        if (taskDto.getId() == null) {
            return ResponseEntity.badRequest().body("TaskId was not specified.");
        }
        TaskDto result = mapper.mapToTaskDto(service.save(mapper.mapToTask(taskDto)));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Rest_api_3", false, "task", taskDto.getId().toString()))
                .body(result);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) throws URISyntaxException {
        if (taskDto.getId() != null) {
            return ResponseEntity.badRequest().body("Task with given id already exists.");
        }

        TaskDto result = mapper.mapToTaskDto(service.save(mapper.mapToTask(taskDto)));
        return ResponseEntity.created(new URI("/v1/task/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("rest_api_3", false, "task", result.getId().toString()))
                .body(result);
    }
}
