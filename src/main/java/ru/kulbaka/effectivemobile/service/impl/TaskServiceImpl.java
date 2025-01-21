package ru.kulbaka.effectivemobile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kulbaka.effectivemobile.dto.*;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.exception.TaskNotFoundException;
import ru.kulbaka.effectivemobile.mapper.TaskMapper;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.repository.TaskRepository;
import ru.kulbaka.effectivemobile.service.TaskService;
import ru.kulbaka.effectivemobile.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Кульбака Никита
 * Сервис для работы с задачами
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserService userService;

    /**
     * Получает все задачи
     *
     * @return список найденных задач
     * @throws TaskNotFoundException если задачи не найдены
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskViewDTO> getAll() throws TaskNotFoundException {
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks not found");
        }

        return tasks.stream()
                .map(taskMapper::toTaskViewDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получает задачу по id
     *
     * @return задача
     * @throws TaskNotFoundException если задача не найдена
     */
    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with id = " + id + " not found"));
    }

    /**
     * Создаёт задачу
     *
     * @return созданная задача
     */
    @Override
    @Transactional
    public TaskViewDTO create(TaskCreateDTO taskCreateDTO) {
        String currentUserEmail = userService.getCurrentUser().getEmail();

        Task task = taskMapper.toTask(taskCreateDTO);
        task.setAuthor(userService.getByEmail(currentUserEmail));
        task.setPerformer(userService.getByEmail(taskCreateDTO.getPerformer()));

        taskRepository.save(task);

        return taskMapper.toTaskViewDTO(task);
    }

    /**
     * Обновляет задачу по id
     *
     * @param id            id задачи
     * @param taskUpdateDTO новые данные задачи
     * @return задача
     * @throws TaskNotFoundException если задача не найдена
     */
    @Override
    @Transactional
    public TaskViewDTO update(Long id, TaskUpdateDTO taskUpdateDTO) throws TaskNotFoundException {
        Task existedTask = getTaskById(id);
        selectiveTaskUpdate(taskUpdateDTO, existedTask);

        taskRepository.save(existedTask);

        return taskMapper.toTaskViewDTO(existedTask);
    }

    /**
     * Удаляет задачу по id
     *
     * @param id id задачи
     * @return удалённая задача
     * @throws TaskNotFoundException если задача не найдена
     */
    @Override
    @Transactional
    public TaskViewDTO delete(Long id) throws TaskNotFoundException {
        Task taskToDelete = getTaskById(id);

        taskRepository.delete(taskToDelete);

        return taskMapper.toTaskViewDTO(taskToDelete);
    }

    /**
     * Изменяет статус задачи по id. Доступен исполнителю и админу
     *
     * @param id                  id задачи
     * @param taskChangeStatusDTO новый статус
     * @return обновлённая задача
     * @throws TaskNotFoundException если задача не найдена
     * @throws AccessDeniedException если нет прав на изменение
     */
    @Override
    @Transactional
    public TaskViewDTO changeStatus(Long id, TaskChangeStatusDTO taskChangeStatusDTO) throws TaskNotFoundException, AccessDeniedException {
        Task taskToChange = getTaskById(id);

        // если пользователь админ или исполнитель
        if (userService.getCurrentUser().getUserRole() == UserRole.ROLE_ADMIN
                || userService.getCurrentUser().getEmail().equals(taskToChange.getPerformer().getEmail())) {
            taskToChange.setStatus(taskChangeStatusDTO.getStatus());

            taskRepository.save(taskToChange);
        } else throw new AccessDeniedException("Insufficient rights");

        return taskMapper.toTaskViewDTO(taskToChange);
    }

    /**
     * Изменяет приоритет задачи по id. Доступен только админу
     *
     * @param id                    id задачи
     * @param taskChangePriorityDTO новый приоритет
     * @return обновлённая задача
     * @throws TaskNotFoundException если задача не найдена
     */
    @Override
    @Transactional
    public TaskViewDTO changePriority(Long id, TaskChangePriorityDTO taskChangePriorityDTO) throws TaskNotFoundException {
        Task taskToChange = getTaskById(id);
        taskToChange.setPriority(taskChangePriorityDTO.getPriority());

        taskRepository.save(taskToChange);

        return taskMapper.toTaskViewDTO(taskToChange);
    }

    /**
     * Получает все задачи конкретного автора с пагинацией. Есть опциональная фильтрация
     *
     * @param taskViewAllByAuthorDTO данные для получения выборки
     * @return список задач
     * @throws TaskNotFoundException если задачи не найдены
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskViewDTO> getAllByAuthor(TaskGetAllByPersonDTO taskViewAllByAuthorDTO) throws TaskNotFoundException {
        User author = userService.getByEmail(taskViewAllByAuthorDTO.getEmail());

        Pageable pageable = PageRequest.of(taskViewAllByAuthorDTO.getOffset(), taskViewAllByAuthorDTO.getLimit());

        Page<Task> taskPage = taskRepository.findAllByAuthor(author, pageable);

        return filterTasksAndMapToDTO(taskViewAllByAuthorDTO, taskPage);
    }

    /**
     * Получает все задачи конкретного исполнителя с пагинацией. Есть опциональная фильтрация
     *
     * @param taskViewAllByPerformerDTO данные для получения выборки
     * @return список задач
     * @throws TaskNotFoundException если задачи не найдены
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskViewDTO> getAllByPerformer(TaskGetAllByPersonDTO taskViewAllByPerformerDTO) {
        User performer = userService.getByEmail(taskViewAllByPerformerDTO.getEmail());

        Pageable pageable = PageRequest.of(taskViewAllByPerformerDTO.getOffset(), taskViewAllByPerformerDTO.getLimit());

        Page<Task> taskPage = taskRepository.findAllByPerformer(performer, pageable);

        return filterTasksAndMapToDTO(taskViewAllByPerformerDTO, taskPage);
    }

    /**
     * Фильтрует по параметрам и формирует итоговую выборку задач
     *
     * @param taskViewAllByPersonDTO данные для фильтрации
     * @param taskPage               задачи
     * @return список задач
     * @throws TaskNotFoundException если в итоговой выборке нет задач
     */
    private List<TaskViewDTO> filterTasksAndMapToDTO(TaskGetAllByPersonDTO taskViewAllByPersonDTO, Page<Task> taskPage) throws TaskNotFoundException {
        List<TaskViewDTO> tasks = taskPage.stream()
                .filter(task -> (taskViewAllByPersonDTO.getStatus() == null
                        || taskViewAllByPersonDTO.getStatus() == task.getStatus()))
                .filter(task -> (taskViewAllByPersonDTO.getPriority() == null
                        || taskViewAllByPersonDTO.getPriority() == task.getPriority()))
                .map(taskMapper::toTaskViewDTO)
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks not found");
        }

        return tasks;
    }

    /**
     * Выборочно обновляет задачу
     *
     * @param taskUpdateDTO данные для обновления
     * @param existedTask   обновляемая задача
     */
    private void selectiveTaskUpdate(TaskUpdateDTO taskUpdateDTO, Task existedTask) {
        if (taskUpdateDTO.getTitle() != null) {
            existedTask.setTitle(taskUpdateDTO.getTitle());
        }
        if (taskUpdateDTO.getDescription() != null) {
            existedTask.setDescription(taskUpdateDTO.getDescription());
        }
        if (taskUpdateDTO.getStatus() != null) {
            existedTask.setStatus(taskUpdateDTO.getStatus());
        }
        if (taskUpdateDTO.getPriority() != null) {
            existedTask.setPriority(taskUpdateDTO.getPriority());
        }
        // если есть новый исполнитель и он не совпадает со старым
        if (taskUpdateDTO.getPerformer() != null && !taskUpdateDTO.getPerformer().equals(existedTask.getPerformer().getEmail())) {
            User newPerformer = userService.getByEmail(taskUpdateDTO.getPerformer());
            existedTask.setPerformer(newPerformer);
        }
    }
}
