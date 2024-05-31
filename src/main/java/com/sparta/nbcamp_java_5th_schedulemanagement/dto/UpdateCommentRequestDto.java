package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentRequestDto {
    private String content;
    private String userId;

    public void validate() {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 비어 있습니다.");
        }
    }
}
