package main.java.org.fastcampus.post.application.Interfaces;

import main.java.org.fastcampus.post.domain.Post;
import main.java.org.fastcampus.post.domain.comment.Comment;
import main.java.org.fastcampus.user.domain.User;

public interface LikeRepository {
    boolean checkLike(Post post, User user);
    void like(Post post, User user);
    void unlike(Post post, User user);

    boolean checkLike(Comment comment, User user);
    void like(Comment comment, User user);
    void unlike(Comment comment, User user);
}
