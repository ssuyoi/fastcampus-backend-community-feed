package org.fastcampus.post.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fastcampus.common.principal.AuthPrincipal;
import org.fastcampus.common.principal.UserPricipal;
import org.fastcampus.common.ui.Response;
import org.fastcampus.post.repository.post_queue.UserPostQueueQueryRepository;
import org.fastcampus.post.ui.dto.GetPostContentResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final UserPostQueueQueryRepository queueQueryRepository;

    @GetMapping("")
    public Response<List<GetPostContentResponseDto>> getPostFeed(@AuthPrincipal UserPricipal userPricipal, Long lastPostId) {
        List<GetPostContentResponseDto> result = queueQueryRepository.getContentResponse(
            userPricipal.getUserId(), lastPostId);
        return Response.ok(result);
    }
}
