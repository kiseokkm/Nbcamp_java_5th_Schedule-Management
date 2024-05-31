package com.sparta.nbcamp_java_5th_schedulemanagement.dto;

import com.sparta.nbcamp_java_5th_schedulemanagement.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String nickname;
    private UserRoleEnum role;

    public UserRoleEnum getRole() {
        return role == null ? UserRoleEnum.USER : role;
    }
}
