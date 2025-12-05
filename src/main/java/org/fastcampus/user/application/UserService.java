package main.java.org.fastcampus.user.application;

import main.java.org.fastcampus.user.application.Interfaces.UserRepository;
import main.java.org.fastcampus.user.application.dto.CreateUserRequestDto;
import main.java.org.fastcampus.user.domain.User;
import main.java.org.fastcampus.user.domain.UserInfo;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequestDto dto) {
        UserInfo info = new UserInfo(dto.name(), dto.profileImageUrl());
        User user = new User(null, info);
        return userRepository.save(user);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
