package com.example.TwitterBackend.Mapper;

import com.example.TwitterBackend.DTO.TweetDto;
import com.example.TwitterBackend.DTO.UserDto;
import com.example.TwitterBackend.Model.Tweet;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.util.TweetUtil;

import java.util.ArrayList;
import java.util.List;


public class TweetDtoMapper {
    public static TweetDto toTweetDto(Tweet tweet, User reqUser){
        UserDto user = UserDtoMapper.toUserDto(tweet.getUser());
        boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweet = TweetUtil.isRetweetByReqUser(reqUser, tweet);
        List <Long> reTweetUserIds = new ArrayList<>();
        for(User user1: tweet.getRetweetUser()){
            reTweetUserIds.add(user1.getId());
        }
        TweetDto tweetDto = new TweetDto();
        tweetDto.setId(tweet.getId());
        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweet().size());
        tweetDto.setTotalRetweets(tweet.getRetweetUser().size());
        tweetDto.setUser(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(isRetweet);
        tweetDto.setRetweetUsersId(reTweetUserIds);
        tweetDto.setReplyTweets(toTweetDtos(tweet.getReplyTweet(), reqUser));
        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;
    }
    public static List<TweetDto> toTweetDtos(List<Tweet> tweets, User reqUser){

        List <TweetDto> tweetDtos = new ArrayList<>();
        for(Tweet tweet: tweets){
            TweetDto tweetDto1 = toReplyTweetDto(tweet, reqUser);
            tweetDtos.add(tweetDto1);
        }
        return tweetDtos;
    }

    private static TweetDto toReplyTweetDto(Tweet tweet, User reqUser) {

        UserDto user = UserDtoMapper.toUserDto(tweet.getUser());
        boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweet = TweetUtil.isRetweetByReqUser(reqUser, tweet);
        List <Long> reTweetUserIds = new ArrayList<>();
        for(User user1: tweet.getRetweetUser()){
            reTweetUserIds.add(user1.getId());
        }
        TweetDto tweetDto = new TweetDto();
        tweetDto.setId(tweet.getId());
        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweet().size());
        tweetDto.setTotalRetweets(tweet.getRetweetUser().size());
        tweetDto.setUser(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(isRetweet);
        tweetDto.setRetweetUsersId(reTweetUserIds);

        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;
    }


}
