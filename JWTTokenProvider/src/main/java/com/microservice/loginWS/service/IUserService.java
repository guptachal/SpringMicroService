package com.microservice.loginWS.service;

import com.microservice.loginWS.entity.User;
import com.microservice.loginWS.exception.UserNotFoundException;
import com.microservice.loginWS.exception.UsernameExistException;

public interface IUserService {

    User register(User user) throws UserNotFoundException, UsernameExistException;

    User findByUsername(String username);

    public User findByUsernameOrEmail(String usernameOrEmail);

    User updateUser(User user);
    User updateUserRole(String role, String username);
    public User getUser(String username);
}
