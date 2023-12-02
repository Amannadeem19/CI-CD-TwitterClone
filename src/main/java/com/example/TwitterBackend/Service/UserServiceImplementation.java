package com.example.TwitterBackend.Service;
import com.example.TwitterBackend.Config.JwtProvider;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Model.Verified;
import com.example.TwitterBackend.Repository.UserRepository;
import com.example.TwitterBackend.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserById(Long userId) throws UserException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserException("User not found with id" + userId));
        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromToken(jwt);
        System.out.println("email" + email);
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not found with email "+ email);
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, User request) throws UserException {
        User user = findUserById(userId);
        System.out.println("requested updated data data");
        System.out.println(request);
        boolean isReqUser = UserUtil.isReqUser(request, user);

        user.setReq_user(isReqUser);

        if(request.getFullName() != null){
            user.setFullName(request.getFullName());
        }
        if(request.getImage() != null){
            user.setImage(request.getImage());
        }
        if(request.getBackgroundImage() != null){
            user.setBackgroundImage(request.getBackgroundImage());
        }
        if(request.getBio() != null){
            user.setBio(request.getBio());
        }
        if(request.getBirthDate() != null){
            user.setBirthDate(request.getBirthDate());
        }
        if(request.getLocation() != null){
            user.setLocation(request.getLocation());
        }
        if(request.getWebsite() != null){
            user.setWebsite(request.getWebsite());
        }
        user.setVerified(new Verified());
        System.out.println("updated user data");
        System.out.println(user);
        return userRepository.save(user);
    }

    @Override
    public User followUser(Long userId, User user) throws UserException {
        User userToFollow = findUserById(userId);
        // user is already a follower to usertofollow then unfollow
        if(user.getFollowings().contains(userToFollow) && userToFollow.getFollowers().contains(user)){
            user.getFollowings().remove(userToFollow);
            userToFollow.getFollowers().remove(user);
        }else{
            user.getFollowings().add(userToFollow);
            userToFollow.getFollowers().add(user);
        }
        userRepository.save(userToFollow);
        userRepository.save(user);
        return userToFollow;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
