package ru.kulbaka.effectivemobile.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.kulbaka.effectivemobile.controller.AdminTaskController;
import ru.kulbaka.effectivemobile.dto.TaskCreateDTO;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.model.TaskPriority;
import ru.kulbaka.effectivemobile.model.TaskStatus;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.repository.TaskRepository;
import ru.kulbaka.effectivemobile.repository.UserRepository;
import ru.kulbaka.effectivemobile.security.JwtServiceImpl;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;
import ru.kulbaka.effectivemobile.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminTaskControllerTest {

    private final String CREATE_TASK_URL = "/api/v1/admin/task";

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userService;

    @Autowired
    private JwtServiceImpl jwtServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    @Autowired
    private AdminTaskController adminTaskController;

    private User user;
    private Long userId;
    private String email;
    private String password;
    private String token;
    private TaskCreateDTO validTaskCreateDTO;

    @BeforeEach
    void setUp() {
        userId = 1L;
        email = "test1@mail.ru";
        password = "1111";

        user = new User(email, password, UserRole.ROLE_ADMIN);
        user.setId(userId);

        validTaskCreateDTO = new TaskCreateDTO(
                "test title",
                "test description",
                TaskStatus.PENDING,
                TaskPriority.LOW,
                user.getEmail());

        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString()))
                .thenReturn(new UserDetailsImpl(userId, email, password, UserRole.ROLE_ADMIN));

        token = jwtServiceImpl.generateToken(new UserDetailsImpl(userId, email, password, UserRole.ROLE_ADMIN));
    }

    @Test
    void createdTask_WhenDataValidTest() throws Exception {
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        //doNothing().when(taskRepository).save(any(Task.class));

        mockMvc.perform(post(CREATE_TASK_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(validTaskCreateDTO))
                )
                .andExpect(status().isCreated());
    }
}
