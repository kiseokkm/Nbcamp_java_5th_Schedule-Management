package com.sparta.nbcamp_java_5th_schedulemanagement;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse<T> {
    private int statusCode;
    private String msg;
    private T data;
}
