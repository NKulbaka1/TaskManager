package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Schema(description = "Форма получения задач пользователя с фильтрацией по приоритету и статусу и пагинацией")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskGetAllByPersonDTO {

    @Schema(description = "Почта пользователя", example = "user@mail.ru")
    @NotBlank(message = "Email does not have to be blank")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "Фильтрация по статусу задач")
    private TaskStatus status;

    @Schema(description = "Фильтрация по приоритету задач")
    private TaskPriority priority;

    @Schema(description = "Номер страницы", example = "0")
    @Min(value = 0, message = "Offset must be at least 0")
    @NotNull(message = "offset does not have to be null")
    private Integer offset;

    @Schema(description = "Количество задач на странице", example = "10")
    @Min(value = 1, message = "Limit must be at least 1")
    @Min(value = 0, message = "Limit must be less then 100")
    @NotNull(message = "limit does not have to be null")
    private Integer limit;
}
