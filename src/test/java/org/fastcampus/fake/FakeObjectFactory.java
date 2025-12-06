package test.java.org.fastcampus.fake;

import main.java.org.fastcampus.post.application.CommentService;
import main.java.org.fastcampus.post.application.Interfaces.CommentRepository;
import main.java.org.fastcampus.post.application.Interfaces.LikeRepository;
import main.java.org.fastcampus.post.application.Interfaces.PostRepository;
import main.java.org.fastcampus.post.application.PostService;
import main.java.org.fastcampus.user.application.Interfaces.UserRelationRepository;
import main.java.org.fastcampus.user.application.Interfaces.UserRepository;
import main.java.org.fastcampus.user.application.UserRelationService;
import main.java.org.fastcampus.user.application.UserService;
import test.java.org.fastcampus.post.repository.FakeCommentRepository;
import test.java.org.fastcampus.post.repository.FakeLikeRepository;
import test.java.org.fastcampus.post.repository.FakePostRepository;
import test.java.org.fastcampus.user.repository.FakeUserRelationRepository;
import test.java.org.fastcampus.user.repository.FakeUserRepository;

public class FakeObjectFactory {
    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();
    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final CommentRepository fakeCommentRepository = new FakeCommentRepository();
    private static final LikeRepository fakeLikeRepository = new FakeLikeRepository();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationService = new UserRelationService(userService, fakeUserRelationRepository);
    private static final PostService postService = new PostService(userService, fakePostRepository, fakeLikeRepository);
    private static final CommentService commentService = new CommentService(userService, postService, fakeCommentRepository, fakeLikeRepository);

    private FakeObjectFactory() {}

    public static UserService getUserService() {
        return userService;
    }

    public static UserRelationService getUserRelationService() {
        return userRelationService;
    }

    public static PostService getPostService() {
        return postService;
    }

    public static CommentService getCommentService() {
        return commentService;
    }
}
