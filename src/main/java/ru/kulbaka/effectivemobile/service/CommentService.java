package ru.kulbaka.effectivemobile.service;

import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;
import ru.kulbaka.effectivemobile.dto.TaskViewDTO;

import java.util.List;

public interface CommentService {

    CommentViewDTO create(CommentCreateDTO commentCreateDTO);

    List<CommentViewDTO> getAllByTaskId(Long taskId);

    CommentViewDTO deleteById(Long commentId);
}
