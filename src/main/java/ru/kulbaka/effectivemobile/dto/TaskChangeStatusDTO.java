package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Schema(description = "Форма изменения приоритета задачи")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskChangeStatusDTO {

    @Schema(description = "Новый статус задачи")
    @NotNull(message = "Status must be in {'PENDING', 'IN_PROGRESS', 'COMPLETED'}")
    private TaskStatus status;

}
