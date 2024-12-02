package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;


@Schema(description = "DTO для отправки комментария клиенту")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentViewDTO {

    @Schema(description = "Id комментария")
    private Long id;

    @Schema(description = "Почта автора комментария")
    private String author;

    @Schema(description = "Текст комментария")
    private String message;
}
