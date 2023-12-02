package com.example.TwitterBackend.Controller;

import com.example.TwitterBackend.DTO.TweetDto;
import com.example.TwitterBackend.Exception.TweetException;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Mapper.TweetDtoMapper;
import com.example.TwitterBackend.Model.Tweet;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Request.TweetReplyRequest;
import com.example.TwitterBackend.Response.ApiResponse;
import com.example.TwitterBackend.Service.TweetService;
import com.example.TwitterBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
        @Autowired
        UserService userService;
        @Autowired
        TweetService tweetService;
        //CREATE TWEET
        @PostMapping("/create")
        public ResponseEntity<TweetDto> createTweet(@RequestBody Tweet req, @RequestHeader("Authorization") String jwt)
        throws UserException, TweetException
        {
            System.out.println(req);
            String tokenWithoutBearer = jwt.substring("Bearer ".length());
            User user = userService.findUserProfileByJwt(tokenWithoutBearer);
            Tweet tweet = tweetService.createTweet(req, user);
            TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
            return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
        }

//        REPLY TWEET
    @PostMapping("/reply")
    public ResponseEntity<TweetDto> replyTweet(@RequestBody TweetReplyRequest req, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException
    {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        Tweet tweet = tweetService.createReply(req, user);
        TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
        return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
    }
//    RETWEET
    @PutMapping("/{tweetId}/retweet")
    public ResponseEntity<TweetDto> reTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException
    {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        Tweet tweet = tweetService.reTweet(tweetId, user);
        TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }
//    get tweet by id
    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetDto> findTweetById(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException
    {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        Tweet tweet = tweetService.findById(tweetId);
        TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }
//    delete specific tweet
    @DeleteMapping("/{tweetId}")
    public ResponseEntity<ApiResponse> deleteTweetById(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException
    {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        tweetService.deleteTweetById(tweetId, user.getId());
        ApiResponse apiResponse = new ApiResponse("Tweet deleted successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    Getting all tweets

    @GetMapping
    public ResponseEntity<List<TweetDto>> getAllTweets(@RequestHeader("Authorization") String jwt)
            throws UserException, TweetException
    {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        System.out.println("get all tweet token"+tokenWithoutBearer);
        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        List<Tweet> tweets = tweetService.findAllTweet();
        List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
        return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
    }

//    get all users tweets
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TweetDto>> getAllUserTweets(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {
        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        List<Tweet> tweets = tweetService.getUserTweet(user);
        List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
        return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
    }
//    USERLIKED TWEETS

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TweetDto>> findLikeTweetsContainsUser(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        String tokenWithoutBearer = jwt.substring("Bearer ".length());
        User user = userService.findUserProfileByJwt(tokenWithoutBearer);
        List<Tweet> tweets = tweetService.findByLikesContainsUser(user);
        List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
        return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
    }


}
