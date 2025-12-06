package main.java.org.fastcampus.post.application.Interfaces;

import java.util.Optional;
import main.java.org.fastcampus.post.domain.comment.Comment;

public interface CommentRepository {

    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
}
