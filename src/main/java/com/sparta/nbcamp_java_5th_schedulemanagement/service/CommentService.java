package com.sparta.nbcamp_java_5th_schedulemanagement.service;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.CommentRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.CommentResponseDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.UpdateCommentRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Comment;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Schedule;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.CommentRepository;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        commentRequestDto.validate();

        Schedule schedule = scheduleRepository.findById(commentRequestDto.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다"));
        Comment comment = new Comment(commentRequestDto.getContent(), commentRequestDto.getUserId(), schedule);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId) {
        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(Long id, UpdateCommentRequestDto updateCommentRequestDto) {
        updateCommentRequestDto.validate();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다"));

        if (!comment.getUserId().equals(updateCommentRequestDto.getUserId())) {
            throw new IllegalArgumentException("댓글 작성자와 현재 사용자 불일치");
        }

        comment.setContent(updateCommentRequestDto.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment);
    }
}
