package com.example.TwitterBackend.Service;

import com.example.TwitterBackend.Exception.TweetException;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Model.Like;
import com.example.TwitterBackend.Model.User;

import java.util.List;

public interface LikeService {
    public Like likeTweet(Long tweetId, User user) throws TweetException, UserException;
    public List<Like> getAllLikes(Long tweetId) throws TweetException;

}
