package org.fastcampus.post.application.Interfaces;

import org.fastcampus.post.domain.comment.Comment;

public interface CommentRepository {
    Comment findById(Long id);
    Comment save(Comment comment);
}
