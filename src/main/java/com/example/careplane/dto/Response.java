package com.example.careplane.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    private String message;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }
}
