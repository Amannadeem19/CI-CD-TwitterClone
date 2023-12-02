package com.example.TwitterBackend.Service;

import com.example.TwitterBackend.Exception.TweetException;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Model.Tweet;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Repository.TweetRepository;
import com.example.TwitterBackend.Request.TweetReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TweetServiceImplementation implements TweetService{
    @Autowired
    private TweetRepository tweetRepository;
    @Override
    public Tweet createTweet(Tweet req, User user) throws UserException {
       Tweet tweet = new Tweet();
       tweet.setContent(req.getContent());
       tweet.setCreatedAt(LocalDateTime.now());
       tweet.setImage(req.getImage());
       tweet.setUser(user);
       tweet.setReply(false);
       tweet.setTweet(true);
       tweet.setVideo(req.getVideo());
       return tweetRepository.save(tweet);
    }
    @Override
    public List<Tweet> findAllTweet() {
        return tweetRepository.findAllByIsTweetTrueOrderByCreatedAtDesc();
    }

    @Override
    public Tweet reTweet(Long tweetId, User user) throws UserException, TweetException {
        Tweet tweet = findById(tweetId);
        if(tweet.getRetweetUser().contains(user)){
            tweet.getRetweetUser().remove(user);
        }else {
            tweet.getRetweetUser().add(user);
        }
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet findById(Long tweetId) throws TweetException {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(()-> new TweetException("Tweet not found with id " + tweetId));
        return tweet;
    }

    @Override
    public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException {
        Tweet tweet = findById(tweetId);
        if(tweet == null){
            throw new TweetException("Tweet not found");
        }
        if (!userId.equals(tweet.getUser().getId())){
            throw new UserException("You can't delete this tweet");
        }
        tweetRepository.deleteById(tweet.getId());
    }

    @Override
    public void removeFromRetweet(Long tweetId, User user) throws TweetException, UserException {

    }

    @Override
    public Tweet createReply(TweetReplyRequest req, User user) throws TweetException, UserException {
        Tweet repliedTweet = findById(req.getTweetId());
        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setReply(true);
        tweet.setTweet(false);
        tweet.setReplyfor(repliedTweet);

        Tweet savedReply = tweetRepository.save(tweet);

        repliedTweet.getReplyTweet().add(savedReply);
        tweetRepository.save(repliedTweet);
        return tweet;
    }

    @Override
    public List<Tweet> getUserTweet(User user) {
        return tweetRepository.findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user, user.getId());
    }

    @Override
    public List<Tweet> findByLikesContainsUser(User user) {
        return tweetRepository.findByLikesUser_id(user.getId());
    }
}
