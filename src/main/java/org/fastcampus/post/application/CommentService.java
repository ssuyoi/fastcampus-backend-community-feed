package org.fastcampus.post.application;

import org.fastcampus.post.application.Interfaces.CommentRepository;
import org.fastcampus.post.application.Interfaces.LikeRepository;
import org.fastcampus.post.application.dto.CreateCommentRequestDto;
import org.fastcampus.post.application.dto.LikeRequestDto;
import org.fastcampus.post.application.dto.UpdateCommentRequestDto;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.domain.comment.Comment;
import org.fastcampus.user.application.UserService;
import org.fastcampus.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public CommentService(UserService userService, PostService postService,
        CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(CreateCommentRequestDto requestDto) {
        Post post = postService.getPost(requestDto.postId());
        User user = userService.getUser(requestDto.userId());

        Comment comment = Comment.createComment(post, user, requestDto.content());
        return commentRepository.save(comment);
    }

    public Comment updateComment(UpdateCommentRequestDto requestDto) {
        Comment comment = getComment(requestDto.commentId());
        User user = userService.getUser(requestDto.userId());

        comment.updateComment(user, requestDto.content());
        return commentRepository.save(comment);
    }

    public void likeComment(LikeRequestDto requestDto) {
        Comment comment = getComment(requestDto.targetId());
        User user = userService.getUser(requestDto.userId());

        if(likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.like(user);
        likeRepository.like(comment, user);
    }

    public void unlikeComment(LikeRequestDto requestDto) {
        Comment comment = getComment(requestDto.targetId());
        User user = userService.getUser(requestDto.userId());
        if(likeRepository.checkLike(comment, user)) {
            comment.unlike();
            likeRepository.unlike(comment, user);
        }
    }

}
