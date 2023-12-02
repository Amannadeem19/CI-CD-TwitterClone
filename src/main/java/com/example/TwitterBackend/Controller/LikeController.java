package com.example.TwitterBackend.Controller;

import com.example.TwitterBackend.DTO.LikeDto;
import com.example.TwitterBackend.Exception.TweetException;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Mapper.LikeDtoMapper;
import com.example.TwitterBackend.Model.Like;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Service.LikeService;
import com.example.TwitterBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;

    @PostMapping("/{tweetId}/likes")
    public ResponseEntity<LikeDto> likeTweet(@PathVariable Long tweetId,
                                             @RequestHeader("Authorization")String jwt)throws UserException, TweetException
    {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user =  userService.findUserProfileByJwt(tokenWithoutBearer);
        Like like = likeService.likeTweet(tweetId, user);
        LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }
    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long tweetId,
                                             @RequestHeader("Authorization")String jwt)throws UserException, TweetException
    {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user =  userService.findUserProfileByJwt(tokenWithoutBearer);
        List <Like> likes = likeService.getAllLikes(tweetId);
        List <LikeDto> likeDtos = LikeDtoMapper.toLikeDtos(likes, user);
        return new ResponseEntity<>(likeDtos, HttpStatus.OK);
    }
}
