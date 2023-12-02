package com.example.TwitterBackend.Repository;

import com.example.TwitterBackend.Model.Tweet;
import com.example.TwitterBackend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    public List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();
    public List<Tweet> findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, Long userId);
    public List<Tweet> findByLikesContainingOrderByCreatedAtDesc(User user);
//    user ne jo tweets like ki hongi woh ajayegi
    @Query("SELECT t from Tweet t JOIN t.likes l where l.user.id =: userId")
    public List<Tweet> findByLikesUser_id(Long userId);

}
