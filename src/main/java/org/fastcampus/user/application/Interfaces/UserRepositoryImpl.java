package org.fastcampus.user.application.Interfaces;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.fastcampus.user.application.repository.entity.UserEntity;
import org.fastcampus.user.application.repository.jpa.JpaUserRepository;
import org.fastcampus.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user);
        entity = jpaUserRepository.save(entity);
        return entity.toUser();
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = jpaUserRepository
            .findById(id)
            .orElseThrow(IllegalArgumentException::new);
        return userEntity.toUser();
    }
}
