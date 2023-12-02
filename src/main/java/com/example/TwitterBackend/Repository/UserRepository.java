package com.example.TwitterBackend.Repository;

import com.example.TwitterBackend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    @Query("Select DISTINCT u from User u where u.fullName LIKE %:query% OR u.email LIKE %:query% ")
    public List<User> searchUser(@Param("query") String query);

}
