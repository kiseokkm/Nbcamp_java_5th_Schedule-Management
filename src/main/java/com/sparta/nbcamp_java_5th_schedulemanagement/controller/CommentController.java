package com.sparta.nbcamp_java_5th_schedulemanagement.controller;

import com.sparta.nbcamp_java_5th_schedulemanagement.CommonResponse;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.CommentRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.CommentResponseDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.UpdateCommentRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommonResponse<CommentResponseDto>> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        try {
            CommentResponseDto comment = commentService.createComment(commentRequestDto);
            return ResponseEntity.ok(
                    CommonResponse.<CommentResponseDto>builder()
                            .statusCode(HttpStatus.OK.value())
                            .msg("댓글이 성공적으로 추가되었습니다.")
                            .data(comment)
                            .build()
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    CommonResponse.<CommentResponseDto>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg(e.getMessage())
                            .build()
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequestDto updateCommentRequestDto) {
        try {
            CommentResponseDto updatedComment = commentService.updateComment(id, updateCommentRequestDto);
            return ResponseEntity.ok(
                    CommonResponse.<CommentResponseDto>builder()
                            .statusCode(HttpStatus.OK.value())
                            .msg("댓글이 성공적으로 수정되었습니다.")
                            .data(updatedComment)
                            .build()
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    CommonResponse.<CommentResponseDto>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getCommentsByScheduleId(@PathVariable Long scheduleId) {
        List<CommentResponseDto> comments = commentService.getCommentsByScheduleId(scheduleId);
        return ResponseEntity.ok(
                CommonResponse.<List<CommentResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("댓글 목록 조회가 완료되었습니다.")
                        .data(comments)
                        .build()
        );
    }
}
