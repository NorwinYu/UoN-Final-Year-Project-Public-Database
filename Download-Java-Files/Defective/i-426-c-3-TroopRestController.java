
package com.tribesbackend.tribes.controllers;

import com.tribesbackend.tribes.models.jsonmodels.TroopModelListResponseJson;
import com.tribesbackend.tribes.repositories.TroopRepository;
import com.tribesbackend.tribes.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class TroopRestController extends BaseController {
    private TroopRepository troopRepository;
    private PurchaseService purchaseService;

    @Autowired
    public TroopRestController(TroopRepository troopRepository, PurchaseService purchaseService) {
        this.troopRepository = troopRepository;
        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "/kingdom/troops")
    public ResponseEntity getTroops() {
        return ResponseEntity.ok(new TroopModelListResponseJson(getCurrentKingdom().getTroops()));
    }

//    @PostMapping(value = "/kingdom/troops")
//    public ResponseEntity createTroop() {
//        Kingdom kingdom = getCurrentKingdom();
//        if (purchaseService.purchasableItem(kingdom.getId(), "troop",1)){
//            Troop newTroop = new Troop();
//            troopRepository.save(newTroop);
//        }
//    }
}
