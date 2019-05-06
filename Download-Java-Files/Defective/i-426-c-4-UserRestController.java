
package com.tribesbackend.tribes.controllers;


import com.tribesbackend.tribes.models.Kingdom;
import com.tribesbackend.tribes.models.ResourcesModel;
import com.tribesbackend.tribes.models.TribesUser;
import com.tribesbackend.tribes.models.jsonmodels.RegistrationInputJson;
import com.tribesbackend.tribes.models.jsonmodels.RegistrationResponseJson;
import com.tribesbackend.tribes.repositories.KingdomRepository;
import com.tribesbackend.tribes.repositories.ResourceRepository;
import com.tribesbackend.tribes.repositories.UserTRepository;
import com.tribesbackend.tribes.security.JWTService;
import com.tribesbackend.tribes.services.resourcesservice.ResourceService;
import com.tribesbackend.tribes.services.responseservice.ErrorMessagesMethods;
import com.tribesbackend.tribes.services.responseservice.OKstatus;
import com.tribesbackend.tribes.services.userservice.LogoutMessages;
import com.tribesbackend.tribes.services.userservice.UserCrudService;
import com.tribesbackend.tribes.services.userservice.UserModelHelpersMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class UserRestController extends BaseController {
    private UserTRepository userTRepository;
    private UserModelHelpersMethods userMethods;
    private UserCrudService userCrudService;
    private KingdomRepository kingdomRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ResourceService resourceService;
    private ResourceRepository resourceRepository;


    @Autowired
    public UserRestController(UserTRepository userTRepository, UserModelHelpersMethods userMethods,
                              UserCrudService userCrudService, KingdomRepository kingdomRepo,
                              BCryptPasswordEncoder bCryptPasswordEncoder, ResourceService resourceService, ResourceRepository resourceRepository) {
        this.userTRepository = userTRepository;
        this.userMethods = userMethods;
        this.userCrudService = userCrudService;
        this.kingdomRepo = kingdomRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
    }

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@Validated @RequestBody RegistrationInputJson regjson) {
        TribesUser newUser = new TribesUser(regjson.getUsername(), bCryptPasswordEncoder.encode(regjson.getPassword()));
        if (userMethods.usernameAlreadyTaken(newUser)) {
            return new ResponseEntity(ErrorMessagesMethods.usernameAlreadyTaken(), HttpStatus.CONFLICT);
        }
        userTRepository.save(newUser);
        Kingdom newKingdom = new Kingdom(regjson.getKingdom(), newUser);
        kingdomRepo.save(newKingdom);
        List<ResourcesModel> newResources = resourceService.newUserResourcesPreFill(newKingdom);    //        newUser.setKingdom(newKingdom);
        newKingdom.setResourcesModel(newResources);
        newUser.setKingdom(newKingdom);
        newResources.forEach(resourcesModel -> resourceRepository.save(resourcesModel));
        return new ResponseEntity(new RegistrationResponseJson(newUser.getId(), newUser.getUsername(),
                newKingdom.getId(), "No avatar yet", 0), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity loginUser(@RequestBody TribesUser tribesUser) {
        if (tribesUser.getUsername() == null || tribesUser.getPassword() == null || tribesUser.getUsername().isEmpty() ||
                tribesUser.getPassword().isEmpty()) {
            return new ResponseEntity(ErrorMessagesMethods.jsonFieldIsEmpty(tribesUser), HttpStatus.BAD_REQUEST);
        } else if (!userTRepository.findTribesUserByUsername(tribesUser.getUsername()).isPresent()) {
            return new ResponseEntity(ErrorMessagesMethods.notSuchUser(tribesUser.getUsername()), HttpStatus.UNAUTHORIZED);
        } else if (bCryptPasswordEncoder.matches((tribesUser.getPassword()), userTRepository.findTribesUserByUsername(tribesUser.getUsername()).get().getPassword())) {
            userTRepository.findTribesUserByUsername(tribesUser.getUsername()).get().setLoggedIn(true);
            userTRepository.save(userTRepository.findTribesUserByUsername(tribesUser.getUsername()).get());
            return new ResponseEntity(new OKstatus(JWTService.createToken(tribesUser.getUsername())), HttpStatus.OK);

        } else return new ResponseEntity(ErrorMessagesMethods.wrongPassword(), HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity logoutUser() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            TribesUser user = userTRepository.findTribesUserByUsername(SecurityContextHolder.getContext()
                    .getAuthentication().getName()).get();
            user.setLoggedIn(false);
            userTRepository.save(user);
            return new ResponseEntity(new LogoutMessages("ok", "Logged out successfully!"), HttpStatus.OK);
        } else
            return new ResponseEntity(new LogoutMessages("error", "Unauthorized request!"), HttpStatus.FORBIDDEN);
    }
}