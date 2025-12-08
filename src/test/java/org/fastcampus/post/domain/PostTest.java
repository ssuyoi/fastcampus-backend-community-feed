package org.fastcampus.post.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.fastcampus.post.domain.contant.PostPublicationState;
import org.fastcampus.user.domain.User;
import org.fastcampus.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

class PostTest {

    private final UserInfo info = new UserInfo("name", "url");
    private final User user =  new User(1L, info);
    private final User otherUser = new User(2L, info);

    private final Post post = Post.createDefaultPost(1L, user, "content");

    @Test
    void givenPostCreated_whenLike_thenLikeCountShouldBe1() {
        //when
        post.like(otherUser);

        //then
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenLikeByOtherUser_thenThrowException() {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> post.like(user));
    }

    @Test
    void givenPostCreated_whenUnlike_thenLikeCountShouldBe0() {
        //when
        post.unlike();

        //then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreatedAndLike_whenUnlike_thenLikeCountShouldBe0() {
        //given
        post.like(otherUser);

        //when
        post.unlike();

        //then
        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenPostCreated_whenUpdateContent_thenUpdateContentShouldUpdated() {
        //given
        String newPostContent = "newPostContent";

        //when
        post.updatePost(user, newPostContent, PostPublicationState.PUBLIC);

        //then
        assertEquals(newPostContent, post.getContent());
    }

    @Test
    void givenPostCreated_whenUpdateOtherUser_thenThrowException() {
        //given
        String newPostContent = "newPostContent";

        //when, then
        assertThrows(IllegalArgumentException.class, () -> post.updatePost(otherUser , newPostContent, PostPublicationState.PUBLIC));
    }

}
