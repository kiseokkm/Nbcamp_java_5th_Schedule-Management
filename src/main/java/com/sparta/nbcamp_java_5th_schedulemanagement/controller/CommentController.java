package com.sparta.nbcamp_java_5th_schedulemanagement.controller;

import com.sparta.nbcamp_java_5th_schedulemanagement.CommonResponse;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.CommentRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.CommentResponseDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.DeleteCommentRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.UpdateCommentRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.security.UserDetailsImpl;
import com.sparta.nbcamp_java_5th_schedulemanagement.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommonResponse<CommentResponseDto>> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentRequestDto.setUserId(userDetails.getUsername());
        CommentResponseDto comment = commentService.createComment(commentRequestDto);
        return buildResponse(HttpStatus.OK, "댓글이 성공적으로 추가되었습니다.", comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequestDto updateCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        updateCommentRequestDto.setUserId(userDetails.getUsername());
        CommentResponseDto updatedComment = commentService.updateComment(id, updateCommentRequestDto);
        return buildResponse(HttpStatus.OK, "댓글이 성공적으로 수정되었습니다.", updatedComment);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getCommentsByScheduleId(@PathVariable Long scheduleId) {
        List<CommentResponseDto> comments = commentService.getCommentsByScheduleId(scheduleId);
        return buildResponse(HttpStatus.OK, "댓글 목록 조회가 완료되었습니다.", comments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(@PathVariable Long id, @RequestBody DeleteCommentRequestDto deleteCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        deleteCommentRequestDto.setUserId(userDetails.getUsername());
        commentService.deleteComment(id, deleteCommentRequestDto);
        return buildResponse(HttpStatus.OK, "댓글이 성공적으로 삭제되었습니다.", null);
    }

    private <T> ResponseEntity<CommonResponse<T>> buildResponse(HttpStatus status, String msg, T data) {
        return ResponseEntity.status(status).body(
                CommonResponse.<T>builder()
                        .statusCode(status.value())
                        .msg(msg)
                        .data(data)
                        .build()
        );
    }
}
