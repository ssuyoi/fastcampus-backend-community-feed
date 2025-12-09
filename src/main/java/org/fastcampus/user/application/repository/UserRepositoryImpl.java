package org.fastcampus.user.application.repository;

import lombok.RequiredArgsConstructor;
import org.fastcampus.user.application.Interfaces.UserRepository;
import org.fastcampus.user.application.repository.entity.UserEntity;
import org.fastcampus.user.application.repository.jpa.JpaUserRepository;
import org.fastcampus.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity =  jpaUserRepository.save(userEntity);
        return userEntity.toUser();
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = jpaUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return userEntity.toUser();
    }
}
