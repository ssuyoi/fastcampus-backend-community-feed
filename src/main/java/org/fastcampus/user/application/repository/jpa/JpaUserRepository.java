package org.fastcampus.user.application.repository.jpa;

import org.fastcampus.user.application.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

}
