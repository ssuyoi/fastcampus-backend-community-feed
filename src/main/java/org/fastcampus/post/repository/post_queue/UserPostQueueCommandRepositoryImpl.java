package org.fastcampus.post.repository.post_queue;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.post.repository.entity.post.UserPostQueueEntity;
import org.fastcampus.post.repository.jpa.JpaPostRepository;
import org.fastcampus.post.repository.jpa.JpaUserPostQueueRespository;
import org.fastcampus.user.repository.entity.UserEntity;
import org.fastcampus.user.repository.jpa.JpaUserRelationRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPostQueueCommandRepositoryImpl implements UserPostQueueCommandRepository{

    private final JpaPostRepository jpaPostRepository;
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserPostQueueRespository jpaUserPostQueueRespository;

    @Override
    @Transactional
    public void publishPost(PostEntity postEntity) {
        UserEntity userEntity = postEntity.getAuthor();
        List<Long> followersIds = jpaUserRelationRepository.findFollower(userEntity.getId());

        List<UserPostQueueEntity> userPostQueueEntities = followersIds.stream()
            .map(userId -> new UserPostQueueEntity(userId, postEntity.getId(), userEntity.getId()))
            .toList();

        jpaUserPostQueueRespository.saveAll(userPostQueueEntities);
    }

    @Override
    @Transactional
    public void saveFollowPost(Long userId, Long targetId) {
        List<Long> postIdList = jpaPostRepository.findAllPostIdByAuthorId(targetId);

        List<UserPostQueueEntity> userPostQueueEntityList = postIdList.stream()
            .map(postId -> new UserPostQueueEntity(userId, postId, targetId))
            .toList();

        jpaUserPostQueueRespository.saveAll(userPostQueueEntityList);
    }

    @Override
    @Transactional
    public void deleteUnfollowPost(Long userId, Long targetId) {
        jpaUserPostQueueRespository.deleteAllByUserIdAndAuthorId(userId, targetId);
    }
}
