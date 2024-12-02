package ru.kulbaka.effectivemobile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateDTO {

    @NotBlank(message = "Title does not have to be blank")
    private String title;

    @NotBlank(message = "Description does not have to be blank")
    private String description;

    @NotNull(message = "Status must be in {'PENDING', 'IN_PROGRESS', 'COMPLETED'}")
    private TaskStatus status;

    @NotNull(message = "Priority must be in {'LOW', 'MEDIUM', 'HIGH'}")
    private TaskPriority priority;

    @NotBlank(message = "Performer does not have to be blank")
    @Email(message = "Email must be valid")
    private String performer;
}
