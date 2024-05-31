package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCommentRequestDto {
    private String userId;

    public void validate() {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("사용자 ID가 비어 있습니다.");
        }
    }
}
