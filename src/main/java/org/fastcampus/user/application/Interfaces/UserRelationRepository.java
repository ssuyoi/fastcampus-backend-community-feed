package main.java.org.fastcampus.user.application.Interfaces;

import main.java.org.fastcampus.user.domain.User;

public interface UserRelationRepository {
    boolean isAlreadyFollow(User user, User targetUser);
    void save(User user, User targetUser);
    void delete(User user, User targetUser);
}
