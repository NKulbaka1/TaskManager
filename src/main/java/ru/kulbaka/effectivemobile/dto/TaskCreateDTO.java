package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Schema(description = "Форма создания или обновления задачи")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateDTO {

    @Schema(description = "Заголовок", example = "Dinner")
    @NotBlank(message = "Title does not have to be blank")
    private String title;

    @Schema(description = "Описание", example = "Make a sandwich")
    @NotBlank(message = "Description does not have to be blank")
    private String description;

    @Schema(description = "Статус задачи")
    @NotNull(message = "Status must be in {'PENDING', 'IN_PROGRESS', 'COMPLETED'}")
    private TaskStatus status;

    @Schema(description = "Приоритет задачи")
    @NotNull(message = "Priority must be in {'LOW', 'MEDIUM', 'HIGH'}")
    private TaskPriority priority;

    @Schema(description = "Исполнитель задачи", example = "user@mail.ru")
    @NotBlank(message = "Performer does not have to be blank")
    @Email(message = "Email must be valid")
    private String performer;
}
