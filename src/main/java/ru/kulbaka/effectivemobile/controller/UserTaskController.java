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
import ru.kulbaka.effectivemobile.dto.*;
import ru.kulbaka.effectivemobile.service.TaskService;

import java.util.List;

@Tag(name = " User Task Controller", description = "Контроллер для управления задачами")
@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class UserTaskController {

    private final TaskService taskService;

    @Operation(
            summary = "Получение всех задач",
            description = """
                    Позволяет получить все задачи
                    Доступ: любой пользователь
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задачи получены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskViewDTO.class)))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задачи не найдены", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get-all")
    public ResponseEntity<List<TaskViewDTO>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @Operation(
            summary = "Получение всех задач по автору",
            description = """
                    Позволяет получить все задачи с конкретным автором
                    Позволяет сделать пагинацию и отфильтровать выборку по статусу и приоритету
                    Доступ: любой пользователь
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задачи получены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskViewDTO.class)))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задачи не найдены", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/get-all-by-author")
    public ResponseEntity<List<TaskViewDTO>> getAllByAuthor(@RequestBody @Valid TaskGetAllByPersonDTO taskViewAllByAuthorDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllByAuthor(taskViewAllByAuthorDTO));
    }

    @Operation(
            summary = "Получение всех задач по исполнителю",
            description = """
                    Позволяет получить все задачи с конкретным исполнителем
                    Позволяет сделать пагинацию и отфильтровать выборку по статусу и приоритету
                    Доступ: любой пользователь
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задачи получены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskViewDTO.class)))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задачи не найдены", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/get-all-by-performer")
    public ResponseEntity<List<TaskViewDTO>> getAllByPerformer(@RequestBody @Valid TaskGetAllByPersonDTO taskViewAllByPerformerDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllByPerformer(taskViewAllByPerformerDTO));
    }

    @Operation(
            summary = "Изменение статуса у задачи",
            description = """
                    Позволяет изменить статус у задачи по id
                    Доступ: админ или исполнитель задачи
                    """,
            parameters = {
                    @Parameter(name = "taskId", description = "id задачи", required = true, schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус изменён", content = @Content(schema = @Schema(implementation = TaskViewDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{taskId}/change-status")
    public ResponseEntity<TaskViewDTO> changeStatus(@PathVariable("taskId") Long taskId, @RequestBody @Valid TaskChangeStatusDTO taskChangeStatusDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.changeStatus(taskId, taskChangeStatusDTO));
    }
}
