package main.java.org.fastcampus.post.application;

import main.java.org.fastcampus.post.application.Interfaces.CommentRepository;
import main.java.org.fastcampus.post.application.Interfaces.LikeRepository;
import main.java.org.fastcampus.post.application.dto.CreateCommentRequestDto;
import main.java.org.fastcampus.post.application.dto.LikeRequestDto;
import main.java.org.fastcampus.post.application.dto.UpdateCommentRequestDto;
import main.java.org.fastcampus.post.domain.Post;
import main.java.org.fastcampus.post.domain.comment.Comment;
import main.java.org.fastcampus.user.application.UserService;
import main.java.org.fastcampus.user.domain.User;

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
        return commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
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
            comment.like(user);
            likeRepository.unlike(comment, user);
        }
    }

}
