package main.java.org.fastcampus.post.application;

import main.java.org.fastcampus.post.application.Interfaces.LikeRepository;
import main.java.org.fastcampus.post.application.Interfaces.PostRepository;
import main.java.org.fastcampus.post.application.dto.CreatePostRequestDto;
import main.java.org.fastcampus.post.application.dto.LikeRequestDto;
import main.java.org.fastcampus.post.domain.Post;
import main.java.org.fastcampus.user.application.UserService;
import main.java.org.fastcampus.user.domain.User;

public class PostService {

    private final UserService userService;
    private PostRepository postRepository;
    private LikeRepository likeRepository;

    public PostService(UserService userService, PostRepository postRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post Not Found"));
    }

    public Post createPost(CreatePostRequestDto dto) {
        User author = userService.getUser(dto.userId());
        Post post = Post.createPost(null, author, dto.content(), dto.state());
        return postRepository.save(post);
    }

    public Post updatePost(Long id, CreatePostRequestDto dto) {
        Post post = getPost(id);
        User user = userService.getUser(dto.userId());

        post.updatePost(user, dto.content(), dto.state());

        return postRepository.save(post);
    }

    public void likePost(LikeRequestDto dto) {
        Post post = getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(post, user)) {
            return;
        }

        post.like(user);
        likeRepository.like(post, user);
    }


    public void unlikePost(LikeRequestDto dto) {
        Post post = getPost(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(post, user)) {
            post.unlike();
            likeRepository.unlike(post, user);
        }
    }


}
