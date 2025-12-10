package org.fastcampus.user.repository.jpa;

import org.fastcampus.user.domain.User;
import org.fastcampus.user.repository.entity.UserRelationEntity;
import org.fastcampus.user.repository.entity.UserRelationEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationEntityId> {

}
