package org.fastcampus.post.repository.jpa;

import org.fastcampus.post.repository.entity.post.UserPostQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserPostQueueRespository extends JpaRepository<UserPostQueueEntity, Long> {
    void deleteAllByUserIdAndAuthorId(Long userId, Long targetId);
}
