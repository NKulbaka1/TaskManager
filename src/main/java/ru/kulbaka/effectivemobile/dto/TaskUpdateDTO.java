package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Schema(description = "Форма обновления задачи")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDTO {

    @Schema(description = "Заголовок", example = "Dinner")
    private String title;

    @Schema(description = "Описание", example = "Make a sandwich")
    private String description;

    @Schema(description = "Статус задачи")
    private TaskStatus status;

    @Schema(description = "Приоритет задачи")
    private TaskPriority priority;

    @Schema(description = "Исполнитель задачи", example = "user@mail.ru")
    @Email(message = "Email must be valid")
    private String performer;
}
