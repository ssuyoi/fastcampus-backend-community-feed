package main.java.org.fastcampus.user.application.Interfaces;

import java.util.Optional;
import main.java.org.fastcampus.user.domain.User;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(long id);
}
