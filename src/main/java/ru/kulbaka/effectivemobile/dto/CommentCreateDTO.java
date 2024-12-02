package ru.kulbaka.effectivemobile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;

@Schema(description = "Форма создания комментария")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDTO {

    @Schema(description = "Id задачи", example = "1")
    @NotNull
    @Min(value = 1, message = "Id must be positive")
    @JsonProperty("task_id")
    private Long taskId;

    @Schema(description = "Текст комментария", example = "Got the task")
    @NotBlank(message = "Message does not have to be blank")
    private String message;

}
