package ru.kulbaka.effectivemobile.dto;

import jakarta.persistence.*;
import lombok.*;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentViewDTO {

    private Long id;

    private String author;

    private String message;
}
