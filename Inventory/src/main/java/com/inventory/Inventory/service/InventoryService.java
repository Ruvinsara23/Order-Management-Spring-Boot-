package com.inventory.Inventory.service;

import com.inventory.Inventory.dto.InventoryDTO;
import com.inventory.Inventory.model.Inventory;
import com.inventory.Inventory.repo.InventoryRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDTO> getAllInventory() {
        List<Inventory> inventoryList = inventoryRepo.findAll();
        return modelMapper.map(inventoryList, new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    public InventoryDTO saveInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = modelMapper.map(inventoryDTO, Inventory.class);
        Inventory savedInventory = inventoryRepo.save(inventory);
        return modelMapper.map(savedInventory, InventoryDTO.class);
    }

    public InventoryDTO updateInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = modelMapper.map(inventoryDTO, Inventory.class);
        Inventory updateInventory = inventoryRepo.save(inventory);
        return modelMapper.map(updateInventory, InventoryDTO.class);
    }

    public String deleteInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryRepo.findById(inventoryDTO.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        inventoryRepo.delete(inventory);
        return "Product deleted successfully";
    }
}

