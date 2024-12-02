package ru.kulbaka.effectivemobile.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/get-all")
    public ResponseEntity<List<TaskViewDTO>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/get-all-by-author")
    public ResponseEntity<List<TaskViewDTO>> getAllByAuthor(@RequestBody @Valid TaskViewAllByPersonDTO taskViewAllByAuthorDTO) {
        return ResponseEntity.ok(taskService.getAllByAuthor(taskViewAllByAuthorDTO));
    }

    @GetMapping("/get-all-by-performer")
    public ResponseEntity<List<TaskViewDTO>> getAllByPerformer(@RequestBody @Valid TaskViewAllByPersonDTO taskViewAllByPerformerDTO) {
        return ResponseEntity.ok(taskService.getAllByPerformer(taskViewAllByPerformerDTO));
    }

    @PatchMapping("/{id}/change-status") // изменение поля "статус"
    public ResponseEntity<TaskViewDTO> changeStatus(@PathVariable("id") Long id, @RequestBody @Valid TaskChangeStatusDTO taskChangeStatusDTO) {
        return ResponseEntity.ok(taskService.changeStatus(id, taskChangeStatusDTO));
    }

    @PatchMapping("/{id}/change-priority") // изменение поля "приоритет"
    public ResponseEntity<TaskViewDTO> changePriority(@PathVariable("id") Long id, @RequestBody @Valid TaskChangePriorityDTO taskChangePriorityDTO) {
        return ResponseEntity.ok(taskService.changePriority(id, taskChangePriorityDTO));
    }
}
