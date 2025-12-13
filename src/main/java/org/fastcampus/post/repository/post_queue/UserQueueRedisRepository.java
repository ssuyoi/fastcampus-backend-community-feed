package org.fastcampus.post.repository.post_queue;

import java.util.List;
import org.fastcampus.post.repository.entity.post.PostEntity;

public interface UserQueueRedisRepository {
    void publishPostToFollowingList(PostEntity postEntity, List<Long> userIdList);
    void publicPostListToFollowerUser(List<PostEntity> postEntityList, Long userId);
    void deleteDeleteFeed(Long userId, Long authorId);
}
