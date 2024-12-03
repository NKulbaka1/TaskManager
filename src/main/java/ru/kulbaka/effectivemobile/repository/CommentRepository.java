package ru.kulbaka.effectivemobile.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulbaka.effectivemobile.entity.Comment;
import ru.kulbaka.effectivemobile.entity.Task;
import ru.kulbaka.effectivemobile.entity.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByTask(Task task, Pageable pageable);
}
