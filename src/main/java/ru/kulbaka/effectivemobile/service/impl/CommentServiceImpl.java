package ru.kulbaka.effectivemobile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kulbaka.effectivemobile.dto.CommentCreateDTO;
import ru.kulbaka.effectivemobile.dto.CommentGetAllWithPaginationDTO;
import ru.kulbaka.effectivemobile.dto.CommentViewDTO;
import ru.kulbaka.effectivemobile.entity.Comment;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.exception.CommentNotFoundException;
import ru.kulbaka.effectivemobile.exception.TaskNotFoundException;
import ru.kulbaka.effectivemobile.mapper.CommentMapper;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.repository.CommentRepository;
import ru.kulbaka.effectivemobile.service.CommentService;
import ru.kulbaka.effectivemobile.service.TaskService;
import ru.kulbaka.effectivemobile.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Кульбака Никита
 * Сервис для работы с комментариями
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final TaskService taskService;

    private final UserService userService;


    /**
     * Создаёт комментарий. Доступен админу и исполнителю задачи
     *
     * @param commentCreateDTO данные для создания комментария
     * @return созданный комментарий
     * @throws AccessDeniedException если недостаточно прав
     */
    @Override
    @Transactional
    public CommentViewDTO create(CommentCreateDTO commentCreateDTO) throws AccessDeniedException {
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

    /**
     * Получает все комментарии к задаче по её id
     *
     * @param taskId id задачи
     * @return список комментариев
     * @throws CommentNotFoundException если комментарии не найдены
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommentViewDTO> getAllByTaskId(Long taskId, CommentGetAllWithPaginationDTO commentGetAllWithPaginationDTO) throws CommentNotFoundException {
        Task task = taskService.getTaskById(taskId);

        Pageable pageable = PageRequest.of(commentGetAllWithPaginationDTO.getOffset(), commentGetAllWithPaginationDTO.getLimit());

        Page<Comment> commentPage = commentRepository.findAllByTask(task, pageable);

        List<CommentViewDTO> comments = commentPage.stream()
                .map(commentMapper::toCommentViewDTO)
                .collect(Collectors.toList());

        if (comments.isEmpty()) {
            throw new CommentNotFoundException("Comments not found");
        }

        return comments;
    }

    /**
     * Удаляет комментарий по id. Доступен админу и исполнителю задачи
     *
     * @param commentId id комментария
     * @return удалённый комментарий
     * @throws CommentNotFoundException если комментарий не найден
     * @throws AccessDeniedException    если недостаточно прав
     */
    @Override
    @Transactional
    public CommentViewDTO deleteById(Long commentId) throws CommentNotFoundException, AccessDeniedException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment not found"));

        if (userService.getCurrentUser().getEmail().equals(comment.getAuthor().getEmail())
                || userService.getCurrentUser().getUserRole() == UserRole.ROLE_ADMIN) {
            commentRepository.delete(comment);
            return commentMapper.toCommentViewDTO(comment);
        } else throw new AccessDeniedException("Insufficient rights");
    }
}
