package com.example.TwitterBackend.Mapper;

import com.example.TwitterBackend.DTO.UserDto;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {
    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
//        boolean isReqUser = UserUtil.isReqUser(reqUser, user);
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setImage(user.getImage());
        userDto.setBackgroundImage(user.getBackgroundImage());
        userDto.setBio(user.getBio());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setFollowers(toUserDtos(user.getFollowers()));
        userDto.setFollowing(toUserDtos(user.getFollowings()));
        userDto.setLocation(user.getLocation());
        userDto.setWebsite(user.getWebsite());
        userDto.setLogin_with_google(user.isLogin_with_google());
        userDto.setReq_user(user.isReq_user());

//        userDto.setVerified(false);

        return userDto;
    }

    public static List<UserDto> toUserDtos(List<User> followers){
       List<UserDto> userDtos = new ArrayList<>();
       for(User user : followers){
           UserDto userDto = new UserDto();
           userDto.setId(user.getId());
           userDto.setEmail(user.getEmail());
           userDto.setFullName(user.getFullName());
           userDto.setImage(user.getImage());
           userDtos.add(userDto);
       }
       return userDtos;
    }

}
