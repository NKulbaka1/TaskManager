package ru.kulbaka.effectivemobile.mapper;

import org.springframework.stereotype.Component;
import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;
import ru.kulbaka.effectivemobile.entity.Comment;

@Component
public class CommentMapper {

    public CommentViewDTO toCommentViewDTO(Comment comment) {
        CommentViewDTO commentViewDTO = new CommentViewDTO();

        commentViewDTO.setId(comment.getId());
        commentViewDTO.setMessage(comment.getMessage());
        commentViewDTO.setAuthor(comment.getAuthor().getEmail());

        return commentViewDTO;
    }

    public Comment toComment(CommentCreateDTO commentCreateDTO) {
        Comment comment = new Comment();

        comment.setMessage(commentCreateDTO.getMessage());

        return comment;
    }
}
