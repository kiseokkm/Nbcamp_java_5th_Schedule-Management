package com.sparta.nbcamp_java_5th_schedulemanagement.repository;

import com.sparta.nbcamp_java_5th_schedulemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
