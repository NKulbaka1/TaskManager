package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskPriority;

@Schema(description = "Форма изменения приоритета задачи")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskChangePriorityDTO {

    @Schema(description = "Новый приоритет задачи")
    @NotNull(message = "Priority must be in {'LOW', 'MEDIUM', 'HIGH'}")
    private TaskPriority priority;

}
