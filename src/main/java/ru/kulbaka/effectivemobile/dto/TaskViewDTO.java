package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.entity.Comment;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

import java.util.List;

@Schema(description = "DTO для отправки задачи пользователю")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskViewDTO {

    @Schema(description = "Id задачи")
    private Long id;

    @Schema(description = "Заголовок задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Статус задачи")
    private TaskStatus status;

    @Schema(description = "Приоритет задачи")
    private TaskPriority priority;

    @Schema(description = "Почта автора задачи")
    private String author;

    @Schema(description = "Исполнитель задачи")
    private String performer;

}
