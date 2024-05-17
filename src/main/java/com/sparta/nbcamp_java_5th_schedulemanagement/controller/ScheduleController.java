package com.sparta.nbcamp_java_5th_schedulemanagement.controller;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @PutMapping("/schedules/{id}/{password}")
    public Long updateSchedule(@PathVariable Long id, @PathVariable String password, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.updateSchedule(id, password, scheduleRequestDto);
    }

    @DeleteMapping("/schedules/{id}/{password}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String password) {
        return scheduleService.deleteSchedule(id, password);
    }
}
