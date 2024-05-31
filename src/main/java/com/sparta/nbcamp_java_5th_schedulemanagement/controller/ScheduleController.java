package com.sparta.nbcamp_java_5th_schedulemanagement.controller;

import com.sparta.nbcamp_java_5th_schedulemanagement.CommonResponse;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.*;
import com.sparta.nbcamp_java_5th_schedulemanagement.security.UserDetailsImpl;
import com.sparta.nbcamp_java_5th_schedulemanagement.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleResponseDto>> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleRequestDto.setManager(userDetails.getUsername());
        ScheduleResponseDto schedule = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.ok(
                CommonResponse.<ScheduleResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("생성이 완료 되었습니다.")
                        .data(schedule)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleResponseDto>>> getAllSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(
                CommonResponse.<List<ScheduleResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("목록 조회가 완료 되었습니다.")
                        .data(schedules)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ScheduleResponseDto>> getSchedule(@PathVariable Long id) {
        ScheduleResponseDto schedule = scheduleService.getSchedule(id);
        return ResponseEntity.ok(
                CommonResponse.<ScheduleResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("단건 조회가 완료 되었습니다.")
                        .data(schedule)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ScheduleResponseDto>> updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto updateScheduleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        updateScheduleRequestDto.setManager(userDetails.getUsername());
        ScheduleResponseDto schedule = scheduleService.updateSchedule(id, updateScheduleRequestDto);
        return ResponseEntity.ok(
                CommonResponse.<ScheduleResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("수정이 완료 되었습니다.")
                        .data(schedule)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteSchedule(@PathVariable Long id, @RequestBody DeleteScheduleRequestDto deleteScheduleRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteSchedule(id, deleteScheduleRequestDto.getPassword(), userDetails.getUsername());
        return ResponseEntity.ok(
                CommonResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("삭제가 완료 되었습니다.")
                        .build()
        );
    }
}
