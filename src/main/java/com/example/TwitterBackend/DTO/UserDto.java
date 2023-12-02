package com.example.TwitterBackend.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserDto {
    private  Long id;
    private String fullName, website, location, birthDate, email, mobile, image, backgroundImage, bio;
    private boolean req_user, login_with_google;
    private List<UserDto> followers = new ArrayList<>();
    private List<UserDto>following = new ArrayList<>();
    private boolean followed;
    private boolean isVerified;
}
