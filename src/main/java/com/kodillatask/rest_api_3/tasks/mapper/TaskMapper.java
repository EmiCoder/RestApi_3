package com.kodillatask.rest_api_3.tasks.mapper;

import com.kodillatask.rest_api_3.tasks.domain.task.Task;
import com.kodillatask.rest_api_3.tasks.domain.task.TaskDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public Task mapToTask(final TaskDto taskDto) {
            return new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getContent());
    }

    public TaskDto mapToTaskDto(final Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getContent());
    }

    public List<TaskDto> mapToTaskDtoList(final List<Task> taskList) {
        return taskList.stream()
                            .map(task -> new TaskDto(task.getId(), task.getTitle(), task.getContent()))
                            .collect(Collectors.toList());
    }
}
