package com.sparta.nbcamp_java_5th_schedulemanagement.controller;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.*;
import com.sparta.nbcamp_java_5th_schedulemanagement.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @GetMapping
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @PutMapping("/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto updateScheduleRequestDto) {
        return scheduleService.updateSchedule(id, updateScheduleRequestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody DeleteScheduleRequestDto deleteScheduleRequestDto) {
        return scheduleService.deleteSchedule(id, deleteScheduleRequestDto);
    }
}
