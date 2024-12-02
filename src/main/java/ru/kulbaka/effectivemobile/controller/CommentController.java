package ru.kulbaka.effectivemobile.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;
import ru.kulbaka.effectivemobile.service.CommentService;

import java.util.List;

@Tag(name = "Comment Сontroller", description = "Контроллер для аутентификации и управления правами")
@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentViewDTO> create(@RequestBody @Valid CommentCreateDTO commentCreateDTO) {
        return ResponseEntity.ok(commentService.create(commentCreateDTO));
    }

    @GetMapping("/get-all/{taskId}")
    public ResponseEntity<List<CommentViewDTO>> getAllByTaskId(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(commentService.getAllByTaskId(taskId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentViewDTO> deleteById(@PathVariable("commentId") Long taskId) {
        return ResponseEntity.ok(commentService.deleteById(taskId));
    }

}
