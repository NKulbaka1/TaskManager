package ru.kulbaka.effectivemobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.entity.Comment;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskViewDTO {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private String author;

    private String performer;

}
