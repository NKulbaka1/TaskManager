package ru.kulbaka.effectivemobile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDTO {

    @Min(value = 1, message = "Id must be positive")
    @JsonProperty("task_id")
    private Long taskId;

    @NotBlank(message = "Message does not have to be blank")
    private String message;

}
