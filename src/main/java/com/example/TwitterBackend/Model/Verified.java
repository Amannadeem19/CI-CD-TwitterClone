package com.example.TwitterBackend.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Verified {
    private boolean status=false;
    private LocalDateTime startedAt, endsAt;
    private String planType;

}
