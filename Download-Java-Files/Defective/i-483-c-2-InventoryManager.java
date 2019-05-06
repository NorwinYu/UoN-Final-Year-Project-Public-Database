package com.mybus.service;

import com.mybus.dao.SupplierDAO;
import com.mybus.dao.impl.InventoryMongoDAO;

import com.mybus.dao.InventoryDAO;
import com.mybus.model.Inventory;
import com.mybus.model.Supplier;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryManager {
    @Autowired
    private InventoryDAO inventoryDAO;

    @Autowired
    private SuppliersManager suppliersManager;

    @Autowired
    private InventoryMongoDAO inventoryMongoDAO;

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private SupplierDAO supplierDAO;

    public Inventory addInventory(Inventory inventory) {
        Inventory duplicateValue = inventoryDAO.findByUniqueId(inventory.getUniqueId());
        if(duplicateValue != null){
            throw new RuntimeException("Inventory with unique Id already exists");
        }
        if(inventory.getSupplierType() != null && inventory.getSupplierType().equals("others")){
            inventory.setSupplierId(addSupplier(inventory));
        }else{
            inventory.setSupplierId(inventory.getSupplierType());
        }
        inventory.setOperatorId(sessionManager.getOperatorId());
        inventory.setRemainingQuantity(inventory.getQuantity());
        return inventoryDAO.save(inventory);
    }

    public Page<Inventory> getAllInventories(String query,Pageable pageable) {
        long total = count(query);
        List<Inventory> inventories = IteratorUtils.toList(inventoryMongoDAO.findAll( query,pageable).iterator());
        Map<String, String> supplierNames = suppliersManager.findNames();
        for(Inventory inventory:inventories){
            inventory.getAttributes().put("supplierName", supplierNames.get(inventory.getSupplierId()));
        }
        Page<Inventory> page = new PageImpl<>(inventories, pageable, total);
        return page;
    }

    public long count(String query) {
        return inventoryMongoDAO.count(query);
    }

    public Inventory getAnInventory(String inventoryId){
        return inventoryDAO.findById(inventoryId).get();
    }


    public Inventory updateInventory(Inventory inventory) {
        Inventory obj = inventoryDAO.findByUniqueId(inventory.getUniqueId());
        if(obj != null && !inventory.getId().equals(obj.getId())){
            throw new RuntimeException("Error in updating");
        }
        if(inventory.getSupplierType() != null && inventory.getSupplierType().equals("others")){
            inventory.setSupplierId(addSupplier(inventory));
        }else{
            inventory.setSupplierId(inventory.getSupplierType());
        }
        Inventory savedInventory = inventoryDAO.findById(inventory.getId()).get();
        savedInventory.setName(inventory.getName());
        savedInventory.setSupplierId(inventory.getSupplierId());
        savedInventory.setPaid(inventory.isPaid());
        savedInventory.setUniqueId(inventory.getUniqueId());
        savedInventory.setQuantity(inventory.getQuantity());
        savedInventory.setRemarks(inventory.getRemarks());
        savedInventory.setOthersSupplierName(inventory.getOthersSupplierName());
        savedInventory.setSupplierType(inventory.getSupplierType());
        return inventoryDAO.save(savedInventory);
    }


    /* To save supplier when inventory supplier type is "others" */

    private String addSupplier(Inventory inventory) {
        Supplier duplicateSupplier = supplierDAO.findByName(inventory.getOthersSupplierName());
            if(duplicateSupplier != null){
                throw new RuntimeException("Supplier already exists");
            }
            Supplier supplier = new Supplier();
            supplier.setName(inventory.getOthersSupplierName());
            supplier.setOperatorId(sessionManager.getOperatorId());
            supplier = supplierDAO.save(supplier);
            return supplier.getId();
    }

    public boolean delete(String id) {
        inventoryDAO.deleteById(id);
        return true;
    }

    public Inventory findById(String id){
        return inventoryDAO.findById(id).get();
    }

    public boolean updateRemainingQuantity(String inventoryId, long quantity){

        return inventoryMongoDAO.updateRemainingCount(inventoryId,quantity);
    }
    public Map<String,String> findInventoryNames(){
        Map<String,String> namesMap = new HashMap<>();
        List<Inventory> inventories = IteratorUtils.toList(inventoryDAO.findAll().iterator());
        for(Inventory inventory:inventories){
            namesMap.put(inventory.getId(),inventory.getName());
        }
        return namesMap;
    }
}