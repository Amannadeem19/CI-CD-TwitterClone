package com.example.TwitterBackend.Controller;

import com.example.TwitterBackend.DTO.UserDto;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Mapper.UserDtoMapper;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Service.UserService;
import com.example.TwitterBackend.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException{
        System.out.println("get user profile" + jwt);
            // Remove the "Bearer " prefix using substring
           String tokenWithoutBearer = jwt.substring("Bearer ".length());

        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException{
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User reqUser = userService.findUserProfileByJwt(tokenWithoutBearer);
        User user = userService.findUserById(userId);

        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

//    SEARCH-USERS
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query, @RequestHeader("Authorization") String jwt) throws UserException{
        String tokenWithoutBearer = jwt.substring("Bearer ".length());

        User reqUser = userService.findUserProfileByJwt(tokenWithoutBearer);
        List <User> users = userService.searchUser(query);

        List<UserDto> userDtos = UserDtoMapper.toUserDtos(users);
        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
    }
//    UPDATE USER
        @PutMapping("/update")
        public ResponseEntity<UserDto> updateUser(@RequestBody User req, @RequestHeader("Authorization") String jwt) throws UserException{
            System.out.println("UPDATED USER DATA");
            System.out.println(req);
            String tokenWithoutBearer = jwt.substring("Bearer ".length());

            User reqUser = userService.findUserProfileByJwt(tokenWithoutBearer);
            User user = userService.updateUser(reqUser.getId(), req);
            UserDto userDto = UserDtoMapper.toUserDto(user);
            return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
        }

//        Follow user
        @PutMapping("/{userId}/follow")
        public ResponseEntity<UserDto> followUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException{
            String tokenWithoutBearer = jwt.substring("Bearer ".length());
            User reqUser = userService.findUserProfileByJwt(tokenWithoutBearer);
            User user = userService.followUser(userId, reqUser);
            UserDto userDto = UserDtoMapper.toUserDto(user);
            userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
            return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
        }


}
