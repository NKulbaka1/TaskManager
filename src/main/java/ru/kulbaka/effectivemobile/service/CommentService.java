package ru.kulbaka.effectivemobile.service;

import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentGetAllWithPaginationDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;

import java.util.List;

public interface CommentService {

    CommentViewDTO create(CommentCreateDTO commentCreateDTO);

    List<CommentViewDTO> getAllByTaskId(Long taskId, CommentGetAllWithPaginationDTO commentGetAllWithPaginationDTO);

    CommentViewDTO deleteById(Long commentId);
}
