package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateScheduleRequestDto {
    private String title;
    private String contents;
    private String manager;
    private String password;
    private String date;

    public void setManager(String manager) {
        this.manager = manager;
    }
}
