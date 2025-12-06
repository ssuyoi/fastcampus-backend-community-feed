package test.java.org.fastcampus.post.application;

import main.java.org.fastcampus.post.application.CommentService;
import main.java.org.fastcampus.post.application.PostService;
import main.java.org.fastcampus.post.application.dto.CreateCommentRequestDto;
import main.java.org.fastcampus.post.application.dto.CreatePostRequestDto;
import main.java.org.fastcampus.post.domain.Post;
import main.java.org.fastcampus.post.domain.contant.PostPublicationState;
import main.java.org.fastcampus.user.application.UserService;
import main.java.org.fastcampus.user.application.dto.CreateUserRequestDto;
import main.java.org.fastcampus.user.domain.User;
import test.java.org.fastcampus.fake.FakeObjectFactory;

public class PostApplicationTestTemplate {
    final UserService userService = FakeObjectFactory.getUserService();
    final PostService postService = FakeObjectFactory.getPostService();
    final CommentService commentService = FakeObjectFactory.getCommentService();

    final User user = userService.createUser(new CreateUserRequestDto("user1", null));
    final User otherUser = userService.createUser(new CreateUserRequestDto("user1", null));

    CreatePostRequestDto postRequestDto = new CreatePostRequestDto(user.getId(), "this is test content",
        PostPublicationState.PUBLIC);
    final Post post = postService.createPost(postRequestDto);

    final String commentContent = "this is test content";
    CreateCommentRequestDto commentRequestDto = new CreateCommentRequestDto(post.getId(), user.getId(), commentContent);
}
