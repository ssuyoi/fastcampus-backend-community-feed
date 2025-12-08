package org.fastcampus.post.application.dto;

//like 기능의 동작 구조가 게시글, 댓글 모두 크게 다르지 않고, 변경될 것 같지 않아서 같이 사용
public record LikeRequestDto(Long targetId, Long userId) {

}
