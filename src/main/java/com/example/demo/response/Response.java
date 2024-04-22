package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Response<T> {
    private String code;
    private String message;
    private T data;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
