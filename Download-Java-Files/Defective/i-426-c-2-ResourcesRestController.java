
package com.tribesbackend.tribes.controllers;

import com.tribesbackend.tribes.models.ResourcesModel;
import com.tribesbackend.tribes.models.jsonmodels.ResourcesModelListResponseJson;
import com.tribesbackend.tribes.repositories.KingdomRepository;
import com.tribesbackend.tribes.services.resourcesservice.ResourceService;
import com.tribesbackend.tribes.services.responseservice.ErrorMessagesMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ResourcesRestController {
    ResourceService resourceService;
    ErrorMessagesMethods errorMessagesMethods;
    KingdomRepository kingdomRepository;

    @Autowired
    public ResourcesRestController(ResourceService resourceService, ErrorMessagesMethods errorMessagesMethods,
                                   KingdomRepository kingdomRepository) {
        this.resourceService = resourceService;
        this.errorMessagesMethods = errorMessagesMethods;
        this.kingdomRepository = kingdomRepository;
    }

    @GetMapping(value = "/kingdom/resources")
    public ResponseEntity getResources() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userName == null || userName.isEmpty()) {
            return new ResponseEntity(errorMessagesMethods.jsonUsernameNotProvided(), HttpStatus.BAD_REQUEST);
        }
        List<ResourcesModel> updatedList = resourceService.resourceDisplayandUpdate(userName, 1);
        ResourcesModelListResponseJson resourcesModelListResponse = new ResourcesModelListResponseJson();
        resourcesModelListResponse.setResources(updatedList);
        return ResponseEntity.ok(resourcesModelListResponse);
    }
}
