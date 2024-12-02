package ru.kulbaka.effectivemobile.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kulbaka.effectivemobile.dto.*;
import ru.kulbaka.effectivemobile.service.TaskService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Admin Task Controller", description = "Контроллер для расширенного управления задачами с правами администратора")
@RestController
@RequestMapping("/api/v1/admin/task")
@RequiredArgsConstructor
public class AdminTaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskViewDTO> create(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        return ResponseEntity.ok(taskService.create(taskCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskViewDTO> update(@PathVariable("id") Long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        return ResponseEntity.ok(taskService.update(id, taskUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskViewDTO> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.delete(id));
    }

//    @PatchMapping("/{id}/change-status")
//    public ResponseEntity<TaskViewDTO> changeStatus(@PathVariable("id") Long id, @RequestBody TaskChangeStatusDTO taskChangeStatusDTO) {
//        return ResponseEntity.ok(taskService.changeStatus(id, taskChangeStatusDTO));
//    }
//
//    @PatchMapping("/{id}/change-priority")
//    public ResponseEntity<TaskViewDTO> changePriority(@PathVariable("id") Long id, @RequestBody TaskChangePriorityDTO taskChangePriorityDTO) {
//        return ResponseEntity.ok(taskService.changePriority(id, taskChangePriorityDTO));
//    }
}
