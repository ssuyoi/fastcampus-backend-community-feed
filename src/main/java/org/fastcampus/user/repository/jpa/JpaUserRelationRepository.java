package org.fastcampus.user.repository.jpa;

import java.util.List;
import org.fastcampus.user.domain.User;
import org.fastcampus.user.repository.entity.UserRelationEntity;
import org.fastcampus.user.repository.entity.UserRelationEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationEntityId> {

    @Query("SELECT u.followingUserId FROM UserRelationEntity  u WHERE u.followerUserId = :userId")
    List<Long> findFollower(Long userId);
}
