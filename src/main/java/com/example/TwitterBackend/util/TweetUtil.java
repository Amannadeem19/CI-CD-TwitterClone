package com.example.TwitterBackend.util;

import com.example.TwitterBackend.Model.Like;
import com.example.TwitterBackend.Model.Tweet;
import com.example.TwitterBackend.Model.User;

public class TweetUtil {
    public final static boolean isLikedByReqUser(User reqUser, Tweet tweet){
        for(Like like : tweet.getLikes()){
            if(like.getUser().getId().equals(reqUser.getId())){
                return true;
            }
        }

        return false;
    }
    public final static boolean isRetweetByReqUser(User reqUser, Tweet tweet){
        for(User user : tweet.getRetweetUser()){
            if(user.getId().equals(reqUser.getId())){
                return true;
            }
        }
        return false;
    }
}
