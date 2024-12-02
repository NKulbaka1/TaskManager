package ru.kulbaka.effectivemobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Admin Task Controller", description = "Контроллер для расширенного управления задачами с правами администратора")
@RestController
@RequestMapping("/api/v1/admin/task")
@RequiredArgsConstructor
public class AdminTaskController {

    private final TaskService taskService;


    @Operation(
            summary = "Создание задачи",
            description = """
                    Позволяет создать задачу
                    Доступ: админ
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Задача создана", content = @Content(schema = @Schema(implementation = TaskViewDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Исполнитель не найден", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<TaskViewDTO> create(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(taskCreateDTO));
    }

    @Operation(
            summary = "Редактирование задачи",
            description = """
                    Позволяет выборочно изменить параметры задачи
                    Доступ: админ
                    """,
            parameters = {
                    @Parameter(name = "taskId", description = "id задачи", required = true, schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача обновлена", content = @Content(schema = @Schema(implementation = TaskViewDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задача или новый исполнитель не найдены", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskViewDTO> update(@PathVariable("taskId") Long taskId, @RequestBody @Valid TaskUpdateDTO taskUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(taskId, taskUpdateDTO));
    }

    @Operation(
            summary = "Изменение приоритета у задачи",
            description = """
                    Позволяет изменить приоритет у задачи по id
                    Доступ: админ
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Приоритет изменён", content = @Content(schema = @Schema(implementation = TaskViewDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}/change-priority")
    public ResponseEntity<TaskViewDTO> changePriority(@PathVariable("id") Long id, @RequestBody @Valid TaskChangePriorityDTO taskChangePriorityDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.changePriority(id, taskChangePriorityDTO));
    }

    @Operation(
            summary = "Удаление задачи",
            description = """
                    Позволяет удалить задачу по id
                    Доступ: админ
                    """,
            parameters = {
                    @Parameter(name = "taskId", description = "id задачи", required = true, schema = @Schema(implementation = String.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача удалена", content = @Content(schema = @Schema(implementation = TaskViewDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Задача не найден", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskViewDTO> delete(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.delete(taskId));
    }
}
