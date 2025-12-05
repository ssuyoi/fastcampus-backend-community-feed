package test.java.org.fastcampus.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.org.fastcampus.user.application.Interfaces.UserRepository;
import main.java.org.fastcampus.user.application.UserService;
import main.java.org.fastcampus.user.application.dto.CreateUserRequestDto;
import main.java.org.fastcampus.user.domain.User;
import main.java.org.fastcampus.user.domain.UserInfo;
import org.junit.jupiter.api.Test;
import test.java.org.fastcampus.user.repository.FakeUserRepository;

class UserServiceTest {
    private UserRepository userRepository = new FakeUserRepository();
    private final UserService userService = new UserService(userRepository);

    @Test
    void givenUserInfoDto_whenCreateUser_thanCanFindUser() {
        //given
        CreateUserRequestDto dto = new CreateUserRequestDto("test", "");

        //when
        User savedUser = userService.createUser(dto);

        //than
        User foundUser = userService.getUser(savedUser.getId());
        UserInfo userInfo = foundUser.getInfo();
        assertEquals(foundUser.getId(), savedUser.getId());
        assertEquals("test", userInfo.getName());
    }

}
