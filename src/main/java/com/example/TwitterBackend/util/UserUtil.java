package com.example.TwitterBackend.util;

import com.example.TwitterBackend.Model.User;

public class UserUtil {

    public final static boolean isReqUser(User reqUser, User user){
        return reqUser.getId().equals(user.getId());
    }

    public final static boolean isFollowedByReqUser(User reqUser, User user){
        return reqUser.getFollowings().contains(user);
    }
}
