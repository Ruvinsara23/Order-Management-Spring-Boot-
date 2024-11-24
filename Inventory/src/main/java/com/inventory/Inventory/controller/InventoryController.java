package com.inventory.Inventory.controller;

import com.inventory.Inventory.dto.InventoryDTO;
import com.inventory.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getinventory")
    public List<InventoryDTO> getProduct() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/getinventory/{itemId}")
    public InventoryDTO getInventoryByItemId(@PathVariable int itemId) {
        return inventoryService.getInventoryByItemId(itemId);
    }

    @PostMapping("/addinventory")
    public InventoryDTO addInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.saveInventory(inventoryDTO);
    }

    @PutMapping("/updateinventory")
    public InventoryDTO updateInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.updateInventory(inventoryDTO);
    }

    @DeleteMapping("/deleteinventory")
    public String deleteInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.deleteInventory(inventoryDTO);
    }

    @DeleteMapping("/deleteinventory/{id}")
    public String deleteInventoryById(@PathVariable int id) {
        return inventoryService.deleteInventoryById(id);
    }
}

