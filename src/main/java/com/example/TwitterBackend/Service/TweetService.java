package com.example.TwitterBackend.Service;

import com.example.TwitterBackend.Exception.TweetException;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Model.Tweet;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Request.TweetReplyRequest;

import java.util.List;

public interface TweetService {

    public Tweet createTweet(Tweet req, User userId) throws UserException;
    public List<Tweet> findAllTweet();
    public Tweet reTweet(Long tweetId, User user)throws UserException, TweetException;
    public Tweet findById(Long tweetId) throws TweetException;

    public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException;
    public void removeFromRetweet(Long tweetId, User user) throws TweetException,UserException;
    public Tweet createReply(TweetReplyRequest req, User user) throws TweetException, UserException;
    public List<Tweet> getUserTweet(User user);

//    user ne jitni tweets like ki hain woh ajayengi
    public List<Tweet> findByLikesContainsUser(User user);

}
