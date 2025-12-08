package org.fastcampus.post.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.fastcampus.post.application.Interfaces.PostRepository;
import org.fastcampus.post.domain.Post;

public class FakePostRepository implements PostRepository {
    private final Map<Long, Post> store = new HashMap<>();

    @Override
    public Post save(Post post) {
        if(post.getId() != null) {
            store.put(post.getId(), post);
            return post;
        }
        long id = store.size();
        Post newPost = new Post(id, post.getAuthor(), post.getContentObject());
        store.put(id, newPost);
        return newPost;
    }

    @Override
    public Optional<Post> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }
}
