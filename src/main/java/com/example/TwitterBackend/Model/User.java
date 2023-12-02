package com.example.TwitterBackend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String fullName, website, location, birthDate, email, password, mobile, image, backgroundImage, bio;
    private boolean req_user, login_with_google;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List <Tweet> tweet = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List <Like> likes = new ArrayList<>();
    @Embedded
    private Verified verified;
    @JsonIgnore
    @ManyToMany
    private List<User> followers = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    private List<User> followings = new ArrayList<>();
}
