package org.fastcampus.post.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fastcampus.post.application.Interfaces.CommentRepository;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.domain.comment.Comment;
import org.fastcampus.post.repository.entity.comment.CommentEntity;
import org.fastcampus.post.repository.jpa.JpaCommentRepository;
import org.fastcampus.post.repository.jpa.JpaPostRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final JpaPostRepository jpaPostRepository;
    private final JpaCommentRepository jpaCommentRepository;

    @Override
    public Comment findById(Long id) {
        CommentEntity entity = jpaCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return entity.toComment();
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        Post targetPost = comment.getPost();
        CommentEntity entity = jpaCommentRepository.save(new CommentEntity(comment));

        if(comment.getId() != null) {
            jpaCommentRepository.updateCommentEntity(entity);
            return comment;
        }
        return entity.toComment();
    }
}
