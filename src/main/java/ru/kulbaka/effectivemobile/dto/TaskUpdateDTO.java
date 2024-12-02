package ru.kulbaka.effectivemobile.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDTO {

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private String performer;
}
