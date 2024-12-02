package ru.kulbaka.effectivemobile.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;
import ru.kulbaka.effectivemobile.entity.Comment;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.exception.CommentNotFoundException;
import ru.kulbaka.effectivemobile.mapper.CommentMapper;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.repository.CommentRepository;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;
import ru.kulbaka.effectivemobile.service.CommentService;
import ru.kulbaka.effectivemobile.service.TaskService;
import ru.kulbaka.effectivemobile.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final TaskService taskService;

    private final UserService userService;


    @Override
    public CommentViewDTO create(CommentCreateDTO commentCreateDTO) {
        Task task = taskService.getTaskById(commentCreateDTO.getTaskId());

        if (userService.getCurrentUser().getEmail().equals(task.getPerformer().getEmail())
                || userService.getCurrentUser().getUserRole() == UserRole.ROLE_ADMIN) {

            Comment comment = commentMapper.toComment(commentCreateDTO);
            comment.setAuthor(userService.getCurrentUser());
            comment.setTask(task);

            commentRepository.save(comment);

            return commentMapper.toCommentViewDTO(comment);
        } else throw new AccessDeniedException("Insufficient rights");
    }

    @Override
    public List<CommentViewDTO> getAllByTaskId(Long taskId) {
        Task task = taskService.getTaskById(taskId);

        List<Comment> comments = commentRepository.findAllByTask(task);

        if (comments.isEmpty()) {
                throw new CommentNotFoundException("Comments not found");
        }

        return comments.stream()
                .map(commentMapper::toCommentViewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentViewDTO deleteById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment not found"));

        if (userService.getCurrentUser().getEmail().equals(comment.getAuthor().getEmail())
                || userService.getCurrentUser().getUserRole() == UserRole.ROLE_ADMIN) {
            commentRepository.delete(comment);
            return commentMapper.toCommentViewDTO(comment);
        } else throw new AccessDeniedException("Insufficient rights");
    }
}
