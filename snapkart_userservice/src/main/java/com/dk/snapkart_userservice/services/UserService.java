package com.dk.snapkart_userservice.services;

import com.dk.snapkart_userservice.exceptions.UserAlreadyPresentException;
import com.dk.snapkart_userservice.models.Session;
import com.dk.snapkart_userservice.models.User;

public interface UserService {

    User signup(String name, String email, String password);
    Session login(String email, String password);
    void logout(Long userId);

    User getUserById(Long userId);
}
