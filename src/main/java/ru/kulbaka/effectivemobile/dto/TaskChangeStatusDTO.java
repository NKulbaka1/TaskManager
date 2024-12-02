package ru.kulbaka.effectivemobile.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.effectivemobile.model.TaskStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskChangeStatusDTO {

    //@NotNull(message = "Status must be in {'PENDING', 'IN_PROGRESS', 'COMPLETED'}")
    private TaskStatus status;

}
