package com.sparta.nbcamp_java_5th_schedulemanagement.controller;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.LoginRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.SignupRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        try {
            authService.signup(requestDto);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);  // 상태 코드 변경
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        try {
            authService.login(requestDto, response);
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
