package ru.kulbaka.effectivemobile.service;

import ru.kulbaka.effectivemobile.dto.*;
import ru.kulbaka.effectivemobile.entity.Task;

import java.util.List;

public interface TaskService {
    List<TaskViewDTO> getAll();

    TaskViewDTO create(TaskCreateDTO taskCreateDTO);

    TaskViewDTO update(Long id, TaskUpdateDTO taskUpdateDTO);

    TaskViewDTO delete(Long id);

    TaskViewDTO changeStatus(Long id, TaskChangeStatusDTO taskChangeStatusDTO);

    TaskViewDTO changePriority(Long id, TaskChangePriorityDTO taskChangePriorityDTO);

    List<TaskViewDTO> getAllByAuthor(TaskGetAllByPersonDTO taskViewAllByAuthorDTO);

    List<TaskViewDTO> getAllByPerformer(TaskGetAllByPersonDTO taskViewAllByPerformerDTO);

    Task getTaskById(Long id);
}
