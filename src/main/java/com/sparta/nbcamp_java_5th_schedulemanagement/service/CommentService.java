package com.sparta.nbcamp_java_5th_schedulemanagement.service;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.*;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Comment;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Schedule;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.CommentRepository;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Schedule schedule = findScheduleById(commentRequestDto.getScheduleId());
        Comment comment = new Comment(commentRequestDto.getContent(), commentRequestDto.getUserId(), schedule);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId) {
        return commentRepository.findByScheduleId(scheduleId).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, UpdateCommentRequestDto updateCommentRequestDto) {
        Comment comment = findCommentById(id);
        validateUser(comment.getUserId(), updateCommentRequestDto.getUserId());
        comment.setContent(updateCommentRequestDto.getContent());
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(Long id, DeleteCommentRequestDto deleteCommentRequestDto) {
        Comment comment = findCommentById(id);
        validateUser(comment.getUserId(), deleteCommentRequestDto.getUserId());
        commentRepository.delete(comment);
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));
    }

    private Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }

    private void validateUser(String commentUserId, String requestUserId) {
        if (!commentUserId.equals(requestUserId)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }
}
