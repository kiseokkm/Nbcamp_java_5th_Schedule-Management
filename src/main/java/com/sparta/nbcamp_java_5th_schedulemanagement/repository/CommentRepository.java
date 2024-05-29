package com.sparta.nbcamp_java_5th_schedulemanagement.repository;

import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleId(Long scheduleId);
}
