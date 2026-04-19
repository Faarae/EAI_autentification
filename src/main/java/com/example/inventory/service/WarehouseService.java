package com.example.inventory.service;

import com.example.inventory.dto.WarehouseRequest;
import com.example.inventory.entity.Warehouse;
import com.example.inventory.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
    }

    public Warehouse createWarehouse(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse(
            request.getCode(),
            request.getName(),
            request.getLocation(),
            request.getCapacity()
        );
        warehouse.setDescription(request.getDescription());
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(Long id, WarehouseRequest request) {
        Warehouse warehouse = getWarehouseById(id);
        warehouse.setCode(request.getCode());
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());
        warehouse.setCapacity(request.getCapacity());
        warehouse.setDescription(request.getDescription());
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        warehouseRepository.delete(warehouse);
    }
}
