package com.example.TwitterBackend.Service;

import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Repository.UserRepository;
import com.mysql.cj.protocol.x.ResultMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username); // username is assume to be an email
        if (user == null || user.isLogin_with_google()) {
            throw new UsernameNotFoundException("Username doesnot found " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails =  org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
        return userDetails;
//        or
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
