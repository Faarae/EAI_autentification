package com.example.inventory.repository;

import com.example.inventory.entity.InventoryAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAlertRepository extends JpaRepository<InventoryAlert, Long> {
}
