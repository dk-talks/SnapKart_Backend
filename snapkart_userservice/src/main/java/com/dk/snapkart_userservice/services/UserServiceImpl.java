package com.dk.snapkart_userservice.services;

import com.dk.snapkart_userservice.config.JwtConfig;
import com.dk.snapkart_userservice.exceptions.UserAlreadyPresentException;
import com.dk.snapkart_userservice.exceptions.InvalidUserException;
import com.dk.snapkart_userservice.models.Session;
import com.dk.snapkart_userservice.models.SessionStatus;
import com.dk.snapkart_userservice.models.User;
import com.dk.snapkart_userservice.repositories.SessionRepository;
import com.dk.snapkart_userservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private SessionRepository sessionRepository;
    private JwtConfig jwtConfig;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           SessionRepository sessionRepository,
                           JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionRepository = sessionRepository;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            // user not found
            throw new InvalidUserException("User not found with id: " + userId);
        }
        return userOptional.get();
    }

    @Override
    public User signup(String name, String email, String password) {

        // check if this email is already present
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(!userOptional.isEmpty()) {
            // user already exist
            throw new UserAlreadyPresentException("User with email already present");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        return userRepository.save(user);
    }

    public Session login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            throw new InvalidUserException("Invalid Email or Password");
        }

        User user = userOptional.get();

        if(!passwordEncoder.matches(password, user.getPassword())) {
            // password does not match
            throw new InvalidUserException("Invalid Email or Password");
        }


        // create a token
        Session session = new Session();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Set current date
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days

        Date dateAfter30Days = calendar.getTime();

        session.setSessionStatus(SessionStatus.LOGGED_IN);
        session.setUser(user);
        session.setExpiryAt(dateAfter30Days);
        session.setTokenValue(jwtConfig.generateToken(user.getId(), user.getEmail(), dateAfter30Days));

        return sessionRepository.save(session);
    }

    public void logout(Long userId) {

        Session session = sessionRepository.findByUserId(userId);
        session.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);

    }


}
