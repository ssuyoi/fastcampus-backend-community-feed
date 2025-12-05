package main.java.org.fastcampus.user.domain;

import java.util.Objects;
import main.java.org.fastcampus.common.domain.PositiveIntegerCounter;

public class User {
    private final Long id;
    private final UserInfo info;
    private final PositiveIntegerCounter followingCounter;
    private final PositiveIntegerCounter followerCounter;

    public User(Long id, UserInfo userInfo) {
        if(userInfo == null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.info = userInfo;
        this.followingCounter = new PositiveIntegerCounter();
        this.followerCounter = new PositiveIntegerCounter();
    }

    public void follow(User targetUser) {
        if(targetUser.equals(this)) {
            throw new IllegalArgumentException();
        }

        followingCounter.increase();
        targetUser.increaseFollowerCounter();

    }

    public void unfollow(User targetUser) {
        if(this.equals(targetUser)) {
            throw new IllegalArgumentException();
        }

        followingCounter.decrease();
        targetUser.decreaseFollowerCounter();
    }

    private void increaseFollowerCounter() {
        followerCounter.increase();
    }

    private void decreaseFollowerCounter() {
        followerCounter.decrease();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public UserInfo getInfo() {
        return info;
    }

    public int followerCount() {
        return followerCounter.getCount();
    }

    public int followingCount() {
        return followingCounter.getCount();
    }

}
