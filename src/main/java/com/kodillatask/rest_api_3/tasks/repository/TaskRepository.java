package com.kodillatask.rest_api_3.tasks.repository;

import com.kodillatask.rest_api_3.tasks.domain.task.Task;
import com.kodillatask.rest_api_3.tasks.domain.task.TaskDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();

    @Override
    Task save(Task task);

    @Override
    Optional<Task> findById(Long id);
}
