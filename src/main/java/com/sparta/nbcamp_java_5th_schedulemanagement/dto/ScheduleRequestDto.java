package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {
    private String title;
    private String contents;
    private String manager;
    private String password;
    private String date;
}
