package ru.kulbaka.effectivemobile.mapper;

import ru.kulbaka.effectivemobile.dto.TaskCreateDTO;
import ru.kulbaka.effectivemobile.dto.TaskViewDTO;
import ru.kulbaka.effectivemobile.entity.Task;

public interface TaskMapper {

    TaskViewDTO toTaskViewDTO(Task task);

    Task toTask(TaskCreateDTO taskCreateDTO);

}
