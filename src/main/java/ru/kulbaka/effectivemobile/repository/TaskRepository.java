package ru.kulbaka.effectivemobile.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByAuthor(User author, Pageable pageable);

    Page<Task> findAllByPerformer(User performer, Pageable pageable);
}
