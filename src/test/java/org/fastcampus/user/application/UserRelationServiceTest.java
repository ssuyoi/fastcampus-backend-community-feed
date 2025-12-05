package test.java.org.fastcampus.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import main.java.org.fastcampus.user.application.Interfaces.UserRelationRepository;
import main.java.org.fastcampus.user.application.Interfaces.UserRepository;
import main.java.org.fastcampus.user.application.UserRelationService;
import main.java.org.fastcampus.user.application.UserService;
import main.java.org.fastcampus.user.application.dto.CreateUserRequestDto;
import main.java.org.fastcampus.user.application.dto.FollowUserRequestDto;
import main.java.org.fastcampus.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.org.fastcampus.user.repository.FakeUserRelationRepository;
import test.java.org.fastcampus.user.repository.FakeUserRepository;

class UserRelationServiceTest {

    private UserRepository userRepository = new FakeUserRepository();
    private UserService userService = new UserService(userRepository);
    private UserRelationRepository userRelationRepository = new FakeUserRelationRepository();
    private final UserRelationService userRelationService = new UserRelationService(userService, userRelationRepository);

    private User user1;
    private User user2;

    private FollowUserRequestDto requestDto;

    @BeforeEach
    void init() {
        CreateUserRequestDto dto = new CreateUserRequestDto("test", "");
        this.user1 = userService.createUser(dto);
        this.user2 = userService.createUser(dto);

        this.requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());
    }


    @Test
    void givenCreateTwoUser_whenFollow_thenuserFollowSaved() {
        //when
        userRelationService.follow(requestDto);

        //then
        assertEquals(1, user1.followingCount());
        assertEquals(1, user2.followerCount());
    }


    @Test
    void givenCreateTwoUserFollowed_whenFollow_thenUserThrowError() {
        //given
        userRelationService.follow(requestDto);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.follow(requestDto));
    }

    @Test
    void givenCreateOneUser_whenFollow_thenThrowError() {
        //given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId(), user1.getId());

        //when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.follow(sameUser));

    }

    @Test
    void givenCreateTwoUserFollow_whenFollow_thenUserUnFollowSaved() {

        //given
        userRelationService.follow(requestDto);

        //when
        userRelationService.unfollow(requestDto);

        //then
        assertEquals(0, user1.followingCount());
        assertEquals(0, user2.followerCount());
    }


    @Test
    void givenCreateTwoUser_whenUnFollow_thenUserThrowError() {

        //when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.unfollow(requestDto));
    }

    @Test
    void givenCreateOneUser_whenUnFollowSelf_thenThrowError() {
        //given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId(), user1.getId());

        //when, then
        assertThrows(IllegalArgumentException.class, () -> userRelationService.unfollow(sameUser));

    }
}
