package com.sparta.nbcamp_java_5th_schedulemanagement.entity;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String contents;
    private String manager;
    private String password;
    private String date;
    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.contents = scheduleRequestDto.getContents();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
        this.date = scheduleRequestDto.getDate();
    }
}
