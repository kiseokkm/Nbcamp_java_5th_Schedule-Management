package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    private String title;
    private String contents;
    private String manager;
    private String password;
    private String date;
}