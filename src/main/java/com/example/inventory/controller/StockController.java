package com.example.inventory.controller;

import com.example.inventory.dto.StockRequest;
import com.example.inventory.dto.StockMovementRequest;
import com.example.inventory.entity.Stock;
import com.example.inventory.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@Valid @RequestBody StockRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockService.createStock(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @Valid @RequestBody StockRequest request) {
        return ResponseEntity.ok(stockService.updateStock(id, request));
    }

    @PostMapping("/move")
    public ResponseEntity<Stock> moveStock(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.ok(stockService.moveStock(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
