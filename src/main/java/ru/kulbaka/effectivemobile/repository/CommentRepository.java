package ru.kulbaka.effectivemobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulbaka.effectivemobile.entity.Comment;
import ru.kulbaka.effectivemobile.entity.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByTask(Task task);
}
