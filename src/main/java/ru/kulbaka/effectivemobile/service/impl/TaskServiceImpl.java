package ru.kulbaka.effectivemobile.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kulbaka.effectivemobile.dto.*;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.exception.TaskNotFoundException;
import ru.kulbaka.effectivemobile.exception.UserNotFoundException;
import ru.kulbaka.effectivemobile.mapper.TaskMapper;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.repository.TaskRepository;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;
import ru.kulbaka.effectivemobile.service.TaskService;
import ru.kulbaka.effectivemobile.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserService userService;

    public List<TaskViewDTO> getAll() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(taskMapper::toTaskViewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with id = " + id + " not found"));
    }

    public TaskViewDTO create(TaskCreateDTO taskCreateDTO) {
        String currentUserEmail = userService.getCurrentUser().getEmail();

        Task task = taskMapper.toTask(taskCreateDTO);
        task.setAuthor(userService.getByEmail(currentUserEmail));
        task.setPerformer(userService.getByEmail(taskCreateDTO.getPerformer()));

        taskRepository.save(task);

        return taskMapper.toTaskViewDTO(task);
    }

    @Override
    public TaskViewDTO update(Long id, TaskUpdateDTO taskUpdateDTO) {
        Task existedTask = getTaskById(id);
        selectiveTaskUpdate(taskUpdateDTO, existedTask);

        taskRepository.save(existedTask);

        return taskMapper.toTaskViewDTO(existedTask);
    }

    @Override
    public TaskViewDTO delete(Long id) {
        Task taskToDelete = getTaskById(id);

        taskRepository.delete(taskToDelete);

        return taskMapper.toTaskViewDTO(taskToDelete);
    }

    @Override
    public TaskViewDTO changeStatus(Long id, TaskChangeStatusDTO taskChangeStatusDTO) {
        Task taskToChange = getTaskById(id);

        // если пользователь админ или исполнитель
        if (userService.getCurrentUser().getUserRole() == UserRole.ROLE_ADMIN
                || userService.getCurrentUser().getEmail().equals(taskToChange.getPerformer().getEmail())) {
            taskToChange.setStatus(taskChangeStatusDTO.getStatus());

            taskRepository.save(taskToChange);
        } else throw new AccessDeniedException("Insufficient rights");

        return taskMapper.toTaskViewDTO(taskToChange);
    }

    @Override
    public TaskViewDTO changePriority(Long id, TaskChangePriorityDTO taskChangePriorityDTO) {
        Task taskToChange = getTaskById(id);
        taskToChange.setPriority(taskChangePriorityDTO.getPriority());

        taskRepository.save(taskToChange);

        return taskMapper.toTaskViewDTO(taskToChange);
    }

    @Override
    public List<TaskViewDTO> getAllByAuthor(TaskViewAllByPersonDTO taskViewAllByAuthorDTO) {
        User author = userService.getByEmail(taskViewAllByAuthorDTO.getEmail());

        Pageable pageable = PageRequest.of(taskViewAllByAuthorDTO.getOffset(), taskViewAllByAuthorDTO.getLimit());

        Page<Task> taskPage = taskRepository.findAllByAuthor(author, pageable);

        return taskPage.stream()
                .map(taskMapper::toTaskViewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskViewDTO> getAllByPerformer(TaskViewAllByPersonDTO taskViewAllByPerformerDTO) {
        User performer = userService.getByEmail(taskViewAllByPerformerDTO.getEmail());

        Pageable pageable = PageRequest.of(taskViewAllByPerformerDTO.getOffset(), taskViewAllByPerformerDTO.getLimit());

        Page<Task> taskPage = taskRepository.findAllByPerformer(performer, pageable);

        return taskPage.stream()
                .filter(task -> (taskViewAllByPerformerDTO.getStatus() == null
                        || taskViewAllByPerformerDTO.getStatus() == task.getStatus()))
                .filter(task -> (taskViewAllByPerformerDTO.getPriority() == null
                        || taskViewAllByPerformerDTO.getPriority() == task.getPriority()))
                .map(taskMapper::toTaskViewDTO)
                .collect(Collectors.toList());
    }

    private void selectiveTaskUpdate(TaskUpdateDTO newTask, Task existedTask) {
        if (newTask.getTitle() != null) {
            existedTask.setTitle(newTask.getTitle());
        }
        if (newTask.getDescription() != null) {
            existedTask.setDescription(newTask.getDescription());
        }
        if (newTask.getStatus() != null) {
            existedTask.setStatus(newTask.getStatus());
        }
        if (newTask.getPriority() != null) {
            existedTask.setPriority(newTask.getPriority());
        }
        if (newTask.getPerformer() != null) { // TODO проверить на одинакогого чела чтобы избежать лишнего запроса в бд
            User newPerformer = userService.getByEmail(newTask.getPerformer());
            existedTask.setPerformer(newPerformer);
        }
    }
}
