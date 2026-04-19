package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public class WarehouseRequest {

    @NotBlank(message = "Warehouse code cannot be empty")
    @Size(min = 2, max = 10, message = "Warehouse code must be between 2 and 10 characters")
    private String code;

    @NotBlank(message = "Warehouse name cannot be empty")
    @Size(min = 3, message = "Warehouse name must be at least 3 characters")
    private String name;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotNull(message = "Capacity is required")
    @DecimalMin(value = "0.1", message = "Capacity must be greater than 0")
    private Double capacity;

    private String description;

    public WarehouseRequest() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getCapacity() { return capacity; }
    public void setCapacity(Double capacity) { this.capacity = capacity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
