package com.clocker.controller;

import com.clocker.entity.*;
import com.clocker.service.IUserService;
import com.clocker.util.Password;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity<String> addUser(@RequestBody User user, UriComponentsBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode authStatus = mapper.createObjectNode();

        RegistrationErrors errors = userService.getRegistrationErrors(user);

        if (!errors.isUsername() && !errors.isEmail() && !errors.isPassword()) {
            // Generate hash for user password.
            String notHashedPassword = user.getPassword();
            String hashedPassword = Password.hashPassword(notHashedPassword);
            user.setPassword(hashedPassword);

            boolean flag = userService.addUser(user);
            if (flag == false) {
                authStatus.put("status", "error");
                arrayNode.add(authStatus);
                return new ResponseEntity<String>(arrayNode.toString(), HttpStatus.CONFLICT);
            }
            authStatus.put("status", "success");
            authStatus.put("token", userService.generateUserToken(user));
            arrayNode.add(authStatus);
            return new ResponseEntity<String>(arrayNode.toString(), HttpStatus.CREATED);
        }
        authStatus.put("status", "error");
        ObjectNode errorsNode = mapper.createObjectNode();
        errorsNode.put("username", errors.isUsername());
        errorsNode.put("password", errors.isPassword());
        errorsNode.put("email", errors.isEmail());
        arrayNode.add(authStatus);
        arrayNode.add(errorsNode);
        return new ResponseEntity<String>(arrayNode.toString(), HttpStatus.CONFLICT);
    }

    @PostMapping("auth")
    public ResponseEntity<String> authUser(@RequestBody LoginForm user, UriComponentsBuilder builder) {
        boolean response = userService.authUser(user);

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode authStatus = mapper.createObjectNode();

        if (response == false) {
            authStatus.put("status", "error");
            arrayNode.add(authStatus);
            return new ResponseEntity<String>(arrayNode.toString(), HttpStatus.FORBIDDEN);
        }
        authStatus.put("status", "success");
        authStatus.put("token", userService.generateUserToken(user));
        arrayNode.add(authStatus);
        return new ResponseEntity<String>(arrayNode.toString(), HttpStatus.OK);
    }

    @PostMapping("isAuthenticated")
    public ResponseEntity<String> isUserAuthenticated(@RequestBody Auth token) {
        boolean isAuth = userService.isUserAuthenticated(token);

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode authStatus = mapper.createObjectNode();
        if (isAuth) {
            AuthUser authUser = userService.getAuthenticatedUser(token);

            authStatus.put("status", "success");
            authStatus.put("username", authUser.getUsername());
            authStatus.put("name", authUser.getName());
            authStatus.put("email", authUser.getEmail());
            arrayNode.add(authStatus);

            return new ResponseEntity<String>(arrayNode.toString(), HttpStatus.OK);
        }
        authStatus.put("status", "error");
        arrayNode.add(authStatus);
        return new ResponseEntity<String>(arrayNode.toString(), HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
