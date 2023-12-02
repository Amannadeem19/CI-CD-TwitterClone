package com.example.TwitterBackend.Request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TweetReplyRequest {
    private String content, image;
    private LocalDateTime createdAt;
    private Long tweetId;

}
