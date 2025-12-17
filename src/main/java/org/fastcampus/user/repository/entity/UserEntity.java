package org.fastcampus.user.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fastcampus.common.domain.PositiveIntegerCounter;
import org.fastcampus.common.repository.entity.TimeBaseEntity;
import org.fastcampus.user.domain.User;
import org.fastcampus.user.domain.UserInfo;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name="community_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String profileImage;
    private Integer followerCount;
    private Integer followingCount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate regDate;


    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileImage = user.getProfileImage();
        this.followerCount = user.followerCount();
        this.followingCount = user.followingCount();
    }

    public User toUser() {
        return User.builder()
            .id(id)
            .info(new UserInfo(name, profileImage))
            .followerCounter(new PositiveIntegerCounter(followerCount))
            .followingCounter(new PositiveIntegerCounter(followingCount))
            .build();
    }


}
