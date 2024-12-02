package ru.kulbaka.effectivemobile.mapper;

import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;
import ru.kulbaka.effectivemobile.entity.Comment;

public interface CommentMapper {

    CommentViewDTO toCommentViewDTO(Comment comment);

    Comment toComment(CommentCreateDTO commentCreateDTO);

}
