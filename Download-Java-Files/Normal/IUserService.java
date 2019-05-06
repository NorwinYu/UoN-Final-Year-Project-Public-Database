package com.clocker.clocker.service;

import com.clocker.clocker.entity.*;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(int id);
    boolean addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    boolean authUser(LoginForm user);
    User getUserByUsername(String username);
    String getUserToken(LoginForm login);
    String getUserToken(User user);
    String generateUserToken(User user);
    String generateUserToken(LoginForm user);
    AuthUser getAuthenticatedUser(Auth token);
    RegistrationErrors getRegistrationErrors(User user);
    boolean isUserAuthenticated(Auth token);
}
