package org.fastcampus.post.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.post.repository.post_queue.UserQueueRedisRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class FakeUserQueueRedisRepository implements UserQueueRedisRepository {

    private final Map<Long, Set<PostEntity>> queue = new HashMap<>();
    @Override
    public void publishPostToFollowingList(PostEntity postEntity, List<Long> userIdList) {
        for(Long userId : userIdList) {
            if(queue.containsKey(userId)) {
                queue.get(userId).add(postEntity);
            } else {
                queue.put(userId, new HashSet<>(List.of(postEntity)));
            }
        }
    }

    @Override
    public void publicPostListToFollowerUser(List<PostEntity> postEntityList, Long userId) {
        if (queue.containsKey(userId)) {
            queue.get(userId).addAll(postEntityList);
        } else {
            queue.put(userId, new HashSet<>(postEntityList));
        }
    }

    @Override
    public void deleteDeleteFeed(Long userId, Long targetUserId) {
        if (queue.containsKey(userId)) {
            queue.get(userId).removeIf(post -> post.getAuthor().getId().equals(targetUserId));
        }
    }

    public List<PostEntity> getPostListByUserId(Long userId) {
        return List.copyOf(queue.get(userId));
    }
}
