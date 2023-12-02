package com.example.TwitterBackend.Mapper;
import com.example.TwitterBackend.DTO.LikeDto;
import com.example.TwitterBackend.DTO.TweetDto;
import com.example.TwitterBackend.DTO.UserDto;
import com.example.TwitterBackend.Model.Like;
import com.example.TwitterBackend.Model.User;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {
    public static LikeDto toLikeDto(Like like, User reqUser){

        UserDto user = UserDtoMapper.toUserDto(like.getUser());
        UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);
        TweetDto tweetDto = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setUser(user);
        likeDto.setTweet(tweetDto);
        return likeDto;
    }
    public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser){
        List<LikeDto> likeDtos = new ArrayList<>();
        for(Like like : likes){
            UserDto userDto = UserDtoMapper.toUserDto(like.getUser());
            TweetDto tweetDto = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setUser(userDto);
            likeDto.setTweet(tweetDto);

           likeDtos.add(likeDto);

        }
        return likeDtos;
    }
}
