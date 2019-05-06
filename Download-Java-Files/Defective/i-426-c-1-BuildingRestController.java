package com.tribesbackend.tribes.controllers;

import com.tribesbackend.tribes.models.Kingdom;
import com.tribesbackend.tribes.models.jsonmodels.BuildingModelListResponseJson;
import com.tribesbackend.tribes.models.Building;
import com.tribesbackend.tribes.models.jsonmodels.BuildingInputJson;
import com.tribesbackend.tribes.models.jsonmodels.CreateBuildingJson;
import com.tribesbackend.tribes.repositories.BuildingRepository;
import com.tribesbackend.tribes.services.PurchaseService;
import com.tribesbackend.tribes.services.TimeService;
import com.tribesbackend.tribes.services.responseservice.ErrorResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class BuildingRestController extends BaseController {
    private BuildingRepository buildingRepo;
    private PurchaseService purchaseService;
    private TimeService timeService;

    @Autowired
    BuildingRestController(BuildingRepository buildingRepo, PurchaseService purchaseService, TimeService timeService) {
        this.buildingRepo = buildingRepo;
        this.purchaseService = purchaseService;
        this.timeService = timeService;
    }

    @GetMapping(value = "/kingdom/buildings")
    public  ResponseEntity getBuildings () {
        List<Building> updatedList = getCurrentKingdom().getBuildings();
        BuildingModelListResponseJson buildingModelListResponse = new BuildingModelListResponseJson();
        buildingModelListResponse.setBuildingList(updatedList);
        return new ResponseEntity(buildingModelListResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/kingdom/buildings")
    public ResponseEntity<Object> createBuilding(@RequestBody CreateBuildingJson createBuildingJson) {
        if (createBuildingJson.getType() == null || createBuildingJson.getType().equals("")) {
            return new ResponseEntity(new ErrorResponseModel("Missing parameter(s): type!"), HttpStatus.BAD_REQUEST);
        } else if (createBuildingJson.getType().equals("farm") || createBuildingJson.getType().equals("mine") || createBuildingJson.getType().equals("barracks") || createBuildingJson.getType().equals("townhall")) {
            Kingdom kingdom = getCurrentKingdom();
            if (purchaseService.purchasableItem(kingdom.getId(), createBuildingJson.getType(), 1)) {
                Building newBuilding = new Building(createBuildingJson.getType(), kingdom);
                newBuilding.setFinishedAt(timeService.finishedAtBuilding(newBuilding.getStartedAt(), createBuildingJson.getType(), 1));
                buildingRepo.save(newBuilding);
                return new ResponseEntity(newBuilding, HttpStatus.OK);
            } else return new ResponseEntity(new ErrorResponseModel("Not enough resources"), HttpStatus.CONFLICT);
        } else return new ResponseEntity(new ErrorResponseModel("Invalid building type"), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(value = "/kingdom/buildings/{id}")
    @ResponseBody
    public ResponseEntity<Object> listTheBuilding(@PathVariable long id) {
        if (buildingRepo.findById(id).isPresent()) {
            return new ResponseEntity(buildingRepo.findById(id), HttpStatus.OK);
        } else return new ResponseEntity(new ErrorResponseModel("Id not found"),
                HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/kingdom/buildings/{id}")
    public ResponseEntity<Object> upgradeOrDowngradeBuilding(@PathVariable long id,
                                                             @RequestBody BuildingInputJson buildingInputJson) {
        // and enough resources
        if (buildingRepo.findById(id).isPresent() && (true)) {
            buildingRepo.findById(id).get().setHP(buildingInputJson.getLevel());
            buildingRepo.save(buildingRepo.findById(id).get());
            return new ResponseEntity(buildingRepo.findById(id).get(), HttpStatus.OK);
        } else if ((buildingInputJson.getLevel() < 0) || buildingInputJson.getLevel() == null
                || buildingInputJson.getLevel().toString().isEmpty()) {
            return new ResponseEntity(new ErrorResponseModel("Missing parameter(s): !"), HttpStatus.BAD_REQUEST);
        } else if (!(buildingRepo.findById(id).isPresent())) {
            return new ResponseEntity(new ErrorResponseModel("Id not found"), HttpStatus.NOT_FOUND);
        }
        //not valid level of the building
        else if (false) {
            return new ResponseEntity(new ErrorResponseModel("Invalid building level"), HttpStatus.NOT_ACCEPTABLE);
        }
        //not enough resource
        else if (false) {
            return new ResponseEntity(new ErrorResponseModel("Not enough resource"), HttpStatus.CONFLICT);
        } else return new ResponseEntity(new ErrorResponseModel("Unexpected error"), HttpStatus.IM_USED);
    }
}
