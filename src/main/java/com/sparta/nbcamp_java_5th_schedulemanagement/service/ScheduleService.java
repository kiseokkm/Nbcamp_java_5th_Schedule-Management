package com.sparta.nbcamp_java_5th_schedulemanagement.service;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.*;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Schedule;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);
        Schedule savedSchedule = scheduleRepository.saveSchedule(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    public ScheduleResponseDto getSchedule(Long id) {
        return scheduleRepository.getSchedule(id);
    }

    public Long updateSchedule(Long id, UpdateScheduleRequestDto updateScheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            if (updateScheduleRequestDto.getPassword().equals(schedule.getPassword())) {
                scheduleRepository.updateSchedule(id, updateScheduleRequestDto);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호 불일치");
            }
        } else {
            throw new IllegalArgumentException("일정이 존재하지 않습니다");
        }
    }

    public Long deleteSchedule(Long id, DeleteScheduleRequestDto deleteScheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            if (deleteScheduleRequestDto.getPassword().equals(schedule.getPassword())) {
                scheduleRepository.deleteSchedule(id);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호 불일치");
            }
        } else {
            throw new IllegalArgumentException("일정이 존재하지 않습니다");
        }
    }
}
