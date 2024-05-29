package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private String userId;
    private Long scheduleId;
}
