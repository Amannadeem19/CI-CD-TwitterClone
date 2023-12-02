package com.example.TwitterBackend.Controller;

import com.example.TwitterBackend.Config.JwtProvider;
import com.example.TwitterBackend.Exception.UserException;
import com.example.TwitterBackend.Model.User;
import com.example.TwitterBackend.Model.Verified;
import com.example.TwitterBackend.Repository.UserRepository;
import com.example.TwitterBackend.Response.AuthResponse;
import com.example.TwitterBackend.Service.CustomerUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsServiceImpl customerUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
            String email = user.getEmail();
            String password = user.getPassword();
            String fullName = user.getFullName();
            String dob = user.getBirthDate();
            String bio = user.getBio();
            User isEmailExist = userRepository.findByEmail(email);
            if(isEmailExist != null){
                throw new UserException("Email already used in an account");
            }
            User userCreated = new User();
            userCreated.setEmail(email);
            userCreated.setPassword(passwordEncoder.encode(password));
            userCreated.setFullName(fullName);
            userCreated.setBirthDate(dob);
            userCreated.setBio(bio);
            userCreated.setVerified(new Verified());

            User savedUser = userRepository.save(userCreated);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),
                savedUser.getPassword()

        );
        System.out.println(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateToken(auth);
        AuthResponse res = new AuthResponse(token, true);
    return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/signin")

    public ResponseEntity<AuthResponse> SignIn(@RequestBody User user){
        String email =  user.getEmail();
        String password = user.getPassword();

        Authentication authentication = authenticate(email, password); // authenticate method defined below
        String token = jwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token, true);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    private Authentication authenticate(String email, String password){
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        System.out.println(userDetails.getPassword() + userDetails.getUsername());
        if(userDetails == null){
            throw new BadCredentialsException("Invalid email");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("invalid email or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
