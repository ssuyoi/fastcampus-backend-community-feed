package org.fastcampus.post.application.dto;

import org.fastcampus.post.domain.contant.PostPublicationState;

public record CreatePostRequestDto(long userId, String content, PostPublicationState state) {

}
