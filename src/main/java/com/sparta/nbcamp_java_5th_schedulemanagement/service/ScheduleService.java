package com.sparta.nbcamp_java_5th_schedulemanagement.service;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Schedule;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleRepository ScheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.ScheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto ScheduleRequestDto) {
        Schedule schedule = new Schedule(ScheduleRequestDto);
        Schedule savedSchedule = ScheduleRepository.saveSchedule(schedule);
        ScheduleResponseDto ScheduleResponseDto = new ScheduleResponseDto(schedule);
        return ScheduleResponseDto;
    }
}
