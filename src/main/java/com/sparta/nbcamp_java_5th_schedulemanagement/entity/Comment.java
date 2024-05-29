package com.sparta.nbcamp_java_5th_schedulemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "commenttable")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String userId;
    
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private LocalDateTime createdAt;

    public Comment(String content, String userId, Schedule schedule) {
        this.content = content;
        this.userId = userId;
        this.schedule = schedule;
        this.createdAt = LocalDateTime.now();
    }
}
