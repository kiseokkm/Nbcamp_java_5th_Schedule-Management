package com.sparta.nbcamp_java_5th_schedulemanagement.service;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.LoginRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.SignupRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.User;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.UserRoleEnum;
import com.sparta.nbcamp_java_5th_schedulemanagement.jwt.JwtUtil;
import com.sparta.nbcamp_java_5th_schedulemanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        UserRoleEnum role = requestDto.getRole();

        validateUsername(username);
        validatePassword(requestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        User user = new User(username, password, nickname, role);
        userRepository.save(user);
        logger.info("회원가입 성공: {}", username);
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

    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        logger.info("로그인 시도: {}", requestDto.getUsername());

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> {
                    logger.error("회원님을 찾을 수 없습니다: {}", requestDto.getUsername());
                    return new IllegalArgumentException("회원님을 찾을 수 없습니다.");
                });

        logger.info("DB에서 가져온 유저: {}", user.getUsername());
        logger.info("DB에서 가져온 비밀번호: {}", user.getPassword());

        boolean passwordMatches = passwordEncoder.matches(requestDto.getPassword(), user.getPassword());
        logger.info("입력한 비밀번호와 저장된 비밀번호가 일치하는가? {}", passwordMatches);

        if (!passwordMatches) {
            logger.error("비밀번호 불일치: 입력한 비밀번호={}, DB 비밀번호={}", requestDto.getPassword(), user.getPassword());
            throw new IllegalArgumentException("회원님을 찾을 수 없습니다.");
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, response);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        logger.info("로그인 성공 및 JWT 생성: {}", token);
    }
}
