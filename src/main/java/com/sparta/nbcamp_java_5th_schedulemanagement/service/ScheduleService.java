package com.sparta.nbcamp_java_5th_schedulemanagement.service;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.*;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Schedule;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto updateScheduleRequestDto) {
        Schedule schedule = findScheduleById(id);
        validateUser(schedule.getManager(), updateScheduleRequestDto.getManager());
        validatePassword(schedule.getPassword(), updateScheduleRequestDto.getPassword());

        updateScheduleFromDto(schedule, updateScheduleRequestDto);
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(updatedSchedule);
    }

    public void deleteSchedule(Long id, String password, String manager) {
        Schedule schedule = findScheduleById(id);
        validateUser(schedule.getManager(), manager);
        validatePassword(schedule.getPassword(), password);
        scheduleRepository.delete(schedule);
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다"));
    }

    private void validateUser(String scheduleManager, String requestManager) {
        if (!scheduleManager.equals(requestManager)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    private void validatePassword(String actualPassword, String inputPassword) {
        if (!Objects.equals(actualPassword, inputPassword)) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }
    }

    private void updateScheduleFromDto(Schedule schedule, UpdateScheduleRequestDto updateScheduleRequestDto) {
        schedule.setTitle(updateScheduleRequestDto.getTitle());
        schedule.setContents(updateScheduleRequestDto.getContents());
        schedule.setDate(updateScheduleRequestDto.getDate());
    }
}
