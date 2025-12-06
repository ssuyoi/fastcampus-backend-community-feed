package main.java.org.fastcampus.post.application.dto;

import main.java.org.fastcampus.post.domain.contant.PostPublicationState;

public record CreatePostRequestDto(long userId, String content, PostPublicationState state) {

}
