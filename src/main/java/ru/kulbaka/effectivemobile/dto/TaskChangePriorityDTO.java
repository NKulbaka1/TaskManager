package ru.kulbaka.effectivemobile.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskPriority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskChangePriorityDTO {

    @NotNull(message = "Priority must be in {'LOW', 'MEDIUM', 'HIGH'}")
    private TaskPriority priority;

}
