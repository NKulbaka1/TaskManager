package ru.kulbaka.effectivemobile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskViewAllByPersonDTO {

    @NotBlank(message = "Email does not have to be blank")
    @Email(message = "Email must be valid")
    private String email;

    private TaskStatus status;

    private TaskPriority priority;

    @Min(value = 0, message = "Offset must be at least 0")
    private Integer offset;

    @Min(value = 1, message = "Limit must be at least 1")
    @Min(value = 0, message = "Limit must be less then 100")
    private Integer limit;
}
