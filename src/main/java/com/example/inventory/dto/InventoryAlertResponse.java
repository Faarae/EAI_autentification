package com.example.inventory.dto;

import java.time.LocalDateTime;

public class InventoryAlertResponse {

    private Long id;
    private Long warehouseId;
    private String warehouseName;
    private Long productId;
    private String productName;
    private String alertType;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime resolvedDate;
    private String message;

    public InventoryAlertResponse() {}

    public InventoryAlertResponse(Long id, Long warehouseId, String warehouseName, Long productId, String productName,
                                  String alertType, String status, LocalDateTime createdDate, LocalDateTime resolvedDate, String message) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.productId = productId;
        this.productName = productName;
        this.alertType = alertType;
        this.status = status;
        this.createdDate = createdDate;
        this.resolvedDate = resolvedDate;
        this.message = message;
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

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getResolvedDate() { return resolvedDate; }
    public void setResolvedDate(LocalDateTime resolvedDate) { this.resolvedDate = resolvedDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
