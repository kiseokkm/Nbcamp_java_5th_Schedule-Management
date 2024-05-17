package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String schedule;
    private String manager;
    private String password;
    private String date;

    public ScheduleResponseDto(Schedule Schedule) {
        this.id = Schedule.getId();
        this.title = Schedule.getTitle();
        this.schedule = Schedule.getContents();
        this.manager = Schedule.getManager();
        this.password = Schedule.getPassword();
        this.date = Schedule.getDate();
    }
}
