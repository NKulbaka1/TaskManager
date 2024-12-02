package ru.kulbaka.effectivemobile.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.kulbaka.effectivemobile.model.UserRole;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "performer", fetch = FetchType.LAZY)
    private List<Task> assignedTasks;

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }


}
