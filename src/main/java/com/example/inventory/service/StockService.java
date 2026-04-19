package com.example.inventory.service;

import com.example.inventory.dto.StockRequest;
import com.example.inventory.dto.StockMovementRequest;
import com.example.inventory.entity.*;
import com.example.inventory.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private InventoryAlertRepository inventoryAlertRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }

    public Stock createStock(StockRequest request) {
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + request.getWarehouseId()));

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

        Stock stock = new Stock(
            warehouse,
            product,
            request.getQuantity(),
            request.getMinimalStock(),
            request.getMaximalStock()
        );
        stock.setLocation(request.getLocation());
        Stock savedStock = stockRepository.save(stock);

        if (request.getQuantity() < request.getMinimalStock()) {
            InventoryAlert alert = new InventoryAlert(
                warehouse,
                product,
                "LOW_STOCK",
                "Stock level " + request.getQuantity() + " is below minimal stock " + request.getMinimalStock()
            );
            inventoryAlertRepository.save(alert);
        }

        return savedStock;
    }

    public Stock updateStock(Long id, StockRequest request) {
        Stock stock = getStockById(id);
        stock.setQuantity(request.getQuantity());
        stock.setMinimalStock(request.getMinimalStock());
        stock.setMaximalStock(request.getMaximalStock());
        stock.setLocation(request.getLocation());
        return stockRepository.save(stock);
    }

    @Transactional
    public Stock moveStock(StockMovementRequest request) {
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + request.getWarehouseId()));

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

        Stock stock = stockRepository.findAll().stream()
            .filter(s -> s.getWarehouse().getId().equals(request.getWarehouseId()) &&
                         s.getProduct().getId().equals(request.getProductId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Stock not found for warehouse and product"));

        if (request.getMovementType().equals("OUT")) {
            if (stock.getAvailableQuantity() < request.getQuantity()) {
                throw new RuntimeException("Insufficient stock. Available: " + stock.getAvailableQuantity());
            }
            stock.setQuantity(stock.getQuantity() - request.getQuantity());
        } else if (request.getMovementType().equals("IN")) {
            stock.setQuantity(stock.getQuantity() + request.getQuantity());
        }

        Stock updatedStock = stockRepository.save(stock);

        StockMovement movement = new StockMovement(
            warehouse,
            product,
            request.getMovementType(),
            request.getQuantity(),
            request.getReference()
        );
        movement.setNotes(request.getNotes());
        stockMovementRepository.save(movement);

        if (updatedStock.getQuantity() < updatedStock.getMinimalStock()) {
            InventoryAlert alert = new InventoryAlert(
                warehouse,
                product,
                "LOW_STOCK",
                "Stock level " + updatedStock.getQuantity() + " is below minimal stock " + updatedStock.getMinimalStock()
            );
            inventoryAlertRepository.save(alert);
        }

        return updatedStock;
    }

    public void deleteStock(Long id) {
        Stock stock = getStockById(id);
        stockRepository.delete(stock);
    }
}
