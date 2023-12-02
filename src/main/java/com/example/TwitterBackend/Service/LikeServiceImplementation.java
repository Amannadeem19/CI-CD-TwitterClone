package com.example.TwitterBackend.Service;
import com.example.TwitterBackend.Exception.TweetException;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Model.Like;
import com.example.TwitterBackend.Model.Tweet;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Repository.LikeRepository;
import com.example.TwitterBackend.Repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImplementation implements LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private TweetRepository tweetRepository;
    @Override
    public Like likeTweet(Long tweetId, User user) throws TweetException, UserException {

        Like isLikeExist = likeRepository.isLikeExist(user.getId(), tweetId);

        System.out.println(isLikeExist);

        if(isLikeExist != null){
            System.out.println("hello");
            likeRepository.deleteById(isLikeExist.getId());
        }
        Tweet tweet = tweetService.findById(tweetId);
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);

        tweet.getLikes().add(savedLike);
        tweetRepository.save(tweet);
        return savedLike;

    }

    @Override
    public List<Like> getAllLikes(Long tweetId) throws TweetException {
      Tweet tweet = tweetService.findById(tweetId);
//      if no tweet found with an id then exception through
        List <Like> likes = likeRepository.findByTweetId(tweetId);
        return likes;
    }
}
