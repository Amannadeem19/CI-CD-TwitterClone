package com.example.TwitterBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content, image, video;
    private LocalDateTime createdAt;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    List<Like> likes = new ArrayList<>();
    @OneToMany
    List<Tweet> replyTweet = new ArrayList<>();
    @ManyToMany
    List<User> retweetUser = new ArrayList<>();
    @ManyToOne
    private Tweet replyfor;
    private boolean isReply, isTweet; // this entity work both for reply as well as tweet



}
