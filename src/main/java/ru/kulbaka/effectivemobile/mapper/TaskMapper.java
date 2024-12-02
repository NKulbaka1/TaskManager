package ru.kulbaka.effectivemobile.mapper;

import org.springframework.stereotype.Component;
import ru.kulbaka.effectivemobile.dto.TaskCreateDTO;
import ru.kulbaka.effectivemobile.dto.TaskViewDTO;
import ru.kulbaka.effectivemobile.entity.Task;

import java.util.ArrayList;

@Component
public class TaskMapper {

    public TaskViewDTO toTaskViewDTO(Task task) {
        TaskViewDTO taskViewDTO = new TaskViewDTO();

        taskViewDTO.setId(task.getId());
        taskViewDTO.setTitle(task.getTitle());
        taskViewDTO.setDescription(task.getDescription());
        taskViewDTO.setStatus(task.getStatus());
        taskViewDTO.setPriority(task.getPriority());
        taskViewDTO.setAuthor(task.getAuthor().getEmail());
        taskViewDTO.setPerformer(task.getPerformer().getEmail());

        return taskViewDTO;
    }

    public Task toTask(TaskCreateDTO taskCreateDTO) {
        Task task = new Task();

        task.setTitle(taskCreateDTO.getTitle());
        task.setDescription(taskCreateDTO.getDescription());
        task.setStatus(taskCreateDTO.getStatus());
        task.setPriority(taskCreateDTO.getPriority());
        task.setComments(new ArrayList<>());

        return task;
    }
}
