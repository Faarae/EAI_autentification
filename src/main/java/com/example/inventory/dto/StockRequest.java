package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public class StockRequest {

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;

    @NotNull(message = "Minimal stock is required")
    @Min(value = 0, message = "Minimal stock cannot be negative")
    private Long minimalStock;

    @NotNull(message = "Maximal stock is required")
    @Min(value = 1, message = "Maximal stock must be at least 1")
    private Long maximalStock;

    private String location;

    public StockRequest() {}

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public Long getMinimalStock() { return minimalStock; }
    public void setMinimalStock(Long minimalStock) { this.minimalStock = minimalStock; }

    public Long getMaximalStock() { return maximalStock; }
    public void setMaximalStock(Long maximalStock) { this.maximalStock = maximalStock; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
