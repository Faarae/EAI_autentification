package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public class ProductRequest {

    @NotBlank(message = "SKU cannot be empty")
    @Size(min = 3, max = 20, message = "SKU must be between 3 and 20 characters")
    private String sku;

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 3, message = "Product name must be at least 3 characters")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    private Double weight;

    @NotNull(message = "Unit size is required")
    @DecimalMin(value = "0.1", message = "Unit size must be greater than 0")
    private Double unitSize;

    private String description;

    public ProductRequest() {}

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getUnitSize() { return unitSize; }
    public void setUnitSize(Double unitSize) { this.unitSize = unitSize; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
