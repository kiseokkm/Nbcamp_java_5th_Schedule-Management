package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private String userId;
    private Long scheduleId;

    public void validate() {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 빕니다.");
        }
        if (scheduleId == null) {
            throw new IllegalArgumentException("일정 ID가 입력이 안됐습니다.");
        }
    }
}
