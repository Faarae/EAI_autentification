package com.example.inventory.service;

import com.example.inventory.dto.WarehouseRequest;
import com.example.inventory.dto.WarehouseResponse;
import com.example.inventory.entity.Warehouse;
import com.example.inventory.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    private WarehouseResponse convertToResponse(Warehouse warehouse) {
        return new WarehouseResponse(
            warehouse.getId(),
            warehouse.getCode(),
            warehouse.getName(),
            warehouse.getLocation(),
            warehouse.getCapacity(),
            warehouse.getUsedCapacity(),
            warehouse.getDescription(),
            warehouse.getActive()
        );
    }

    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    public WarehouseResponse getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
        return convertToResponse(warehouse);
    }

    public WarehouseResponse createWarehouse(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse(
            request.getCode(),
            request.getName(),
            request.getLocation(),
            request.getCapacity()
        );
        warehouse.setDescription(request.getDescription());
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return convertToResponse(savedWarehouse);
    }

    public WarehouseResponse updateWarehouse(Long id, WarehouseRequest request) {
        Warehouse warehouse = warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
        warehouse.setCode(request.getCode());
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());
        warehouse.setCapacity(request.getCapacity());
        warehouse.setDescription(request.getDescription());
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);
        return convertToResponse(updatedWarehouse);
    }

    public void deleteWarehouse(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
        warehouseRepository.delete(warehouse);
    }
}
