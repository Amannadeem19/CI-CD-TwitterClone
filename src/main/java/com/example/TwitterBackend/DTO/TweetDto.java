package com.example.TwitterBackend.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TweetDto {
    private Long id;
    private String content, image, video;
    private UserDto user;
    private int totalLikes, totalReplies, totalRetweets;
    private LocalDateTime createdAt;
    private boolean isLiked, isRetweet;
    private List<Long> retweetUsersId;
    private List<TweetDto> replyTweets;

}
