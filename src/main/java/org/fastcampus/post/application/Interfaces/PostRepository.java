package main.java.org.fastcampus.post.application.Interfaces;

import java.util.Optional;
import main.java.org.fastcampus.post.domain.Post;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(long id);
}
