package ru.kulbaka.effectivemobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;
import ru.kulbaka.effectivemobile.dto.TokenDTO;
import ru.kulbaka.effectivemobile.service.CommentService;

import java.util.List;

@Tag(name = "Comment Сontroller", description = "Контроллер для аутентификации и управления правами")
@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Создание комментария",
            description = """
                    Позволяет оставить комментарий под задачей
                    Доступ: админ или исполнитель задачи
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Комментарий создан", content = @Content(schema = @Schema(implementation = CommentViewDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<CommentViewDTO> create(@RequestBody @Valid CommentCreateDTO commentCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(commentCreateDTO));
    }

    @Operation(
            summary = "Получение комментариев к задаче",
            description = """
                    Позволяет получить все комментарии к задаче с указанным id
                    Доступ: любой пользователь
                    """,
            parameters = {
                    @Parameter(name = "taskId", description = "id задачи", required = true, schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Комментарии получены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentViewDTO.class)))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Комментарии или задача не найдены", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get-all/{taskId}")
    public ResponseEntity<List<CommentViewDTO>> getAllByTaskId(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllByTaskId(taskId));
    }

    @Operation(
            summary = "Удаление комментария",
            description = """
                    Позволяет удалить комментарий по id
                    Доступ: админ или исполнитель задачи, если комментарий под его задачей
                    """,
            parameters = {
                    @Parameter(name = "commentId", description = "id комментария", required = true, schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Комментарий удалён", content = @Content(schema = @Schema(implementation = CommentViewDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentViewDTO> deleteById(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteById(commentId));
    }

}
