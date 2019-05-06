package com.clocker.service;

import com.clocker.dao.IUserDAO;
import com.clocker.entity.*;
import com.clocker.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public User getUserById(int id) {
        User obj = userDAO.getUserById(id);
        return obj;
    }

    @Override
    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    @Override
    public synchronized boolean addUser(User user){
        if (userDAO.userExists(user.getUsername(), user.getEmail())) {
            return false;
        } else {
            userDAO.addUser(user);
            return true;
        }
    }

    @Override
    public synchronized boolean authUser(LoginForm user){
        if (userDAO.userExists(user.getUsername())) {
            // if user exists check passwords
            User userModel = userDAO.getUserByUsername(user.getUsername());
            String inputPassword = user.getPassword();
            boolean checkPassword = Password.checkPassword(inputPassword, userModel.getPassword());
            if (checkPassword) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username){
        if (userDAO.userExists(username)) {
            User obj = userDAO.getUserByUsername(username);
            return obj;
        } else {
            return null;
        }
    }

    @Override
    public boolean isUserAuthenticated(Auth token){
        AuthUser user = null;
        if (token.getAuthToken() != null) {
            user = userDAO.getUserByAuthToken(token.getAuthToken());
        }

        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public String getUserToken(LoginForm login){
        String token = null;
        if (login.getUsername() != null) {
            token = userDAO.getUserAuthTokenByUsername(login.getUsername());
        }

        if (token != null) {
            return token;
        }
        return null;
    }

    @Override
    public String getUserToken(User user){
        String token = null;
        if (user.getUsername() != null) {
            token = userDAO.getUserAuthTokenByUsername(user.getUsername());
        }

        if (token != null) {
            return token;
        }
        return null;
    }

    @Override
    public String generateUserToken(User user){
        String token = null;
        if (user.getUsername() != null) {
            token = userDAO.generateUserToken(user.getUsername());
        }

        if (token != null) {
            return token;
        }
        return null;
    }

    @Override
    public String generateUserToken(LoginForm user){
        String token = null;
        if (user.getUsername() != null) {
            token = userDAO.generateUserToken(user.getUsername());
        }

        if (token != null) {
            return token;
        }
        return null;
    }

    @Override
    public AuthUser getAuthenticatedUser(Auth token){
        AuthUser user = null;
        if (token.getAuthToken() != null) {
            user = userDAO.getUserByAuthToken(token.getAuthToken());
        }

        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public RegistrationErrors getRegistrationErrors(User user){
        RegistrationErrors errors = new RegistrationErrors();

        if (!(user.getUsername().length() > 0)) {
            errors.setUsername(true);
        }
        if (!(user.getPassword().length() > 0)) {
            errors.setPassword(true);
        }
        if (!(user.getEmail().length() > 0)) {
            errors.setEmail(true);
        }

        if (!errors.isUsername()) {
            if (userDAO.userExists(user.getUsername())) {
                // Username already exists
                errors.setUsername(true);
            }
        }

        if (errors != null) {
            return errors;
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
