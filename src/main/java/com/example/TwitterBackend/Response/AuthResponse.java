package com.example.TwitterBackend.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private boolean status;
}
