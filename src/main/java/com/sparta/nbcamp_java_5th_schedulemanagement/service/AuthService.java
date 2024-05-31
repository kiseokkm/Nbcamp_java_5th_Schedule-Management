package com.sparta.nbcamp_java_5th_schedulemanagement.service;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.LoginRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.SignupRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.User;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.UserRoleEnum;
import com.sparta.nbcamp_java_5th_schedulemanagement.jwt.JwtUtil;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final HttpServletResponse response;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, HttpServletResponse response) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.response = response;
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        UserRoleEnum role = requestDto.getRole();

        validateUsername(username);
        validatePassword(password);

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        User user = new User(username, password, nickname, role);
        userRepository.save(user);
    }

    private void validateUsername(String username) {
        String usernamePattern = "^[a-z0-9]{4,10}$";
        if (!Pattern.matches(usernamePattern, username)) {
            throw new IllegalArgumentException("username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
        }
    }

    private void validatePassword(String password) {
        String passwordPattern = "^[a-zA-Z0-9]{8,15}$";
        if (!Pattern.matches(passwordPattern, password)) {
            throw new IllegalArgumentException("password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.");
        }
    }

    public void login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("회원님을 찾을 수 없습니다."));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("회원님을 찾을 수 없습니다.");
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, response);
    }
}
