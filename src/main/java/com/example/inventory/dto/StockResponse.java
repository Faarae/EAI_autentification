package com.example.inventory.dto;

public class StockResponse {

    private Long id;
    private Long warehouseId;
    private String warehouseName;
    private Long productId;
    private String productName;
    private Long quantity;
    private Long reservedQuantity;
    private Long availableQuantity;
    private Long minimalStock;
    private Long maximalStock;
    private String location;

    public StockResponse() {}

    public StockResponse(Long id, Long warehouseId, String warehouseName, Long productId, String productName, 
                         Long quantity, Long reservedQuantity, Long availableQuantity, Long minimalStock, 
                         Long maximalStock, String location) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
        this.availableQuantity = availableQuantity;
        this.minimalStock = minimalStock;
        this.maximalStock = maximalStock;
        this.location = location;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public Long getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(Long reservedQuantity) { this.reservedQuantity = reservedQuantity; }

    public Long getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Long availableQuantity) { this.availableQuantity = availableQuantity; }

    public Long getMinimalStock() { return minimalStock; }
    public void setMinimalStock(Long minimalStock) { this.minimalStock = minimalStock; }

    public Long getMaximalStock() { return maximalStock; }
    public void setMaximalStock(Long maximalStock) { this.maximalStock = maximalStock; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
