package com.inventory.Inventory.repo;

import com.inventory.Inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value="SELECT * FROM inventory WHERE item_id=?1", nativeQuery= true)
    Inventory getInventoryByItemId(Integer itemId);
}
