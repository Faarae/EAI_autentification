package com.example.inventory.dto;

public class WarehouseResponse {

    private Long id;
    private String code;
    private String name;
    private String location;
    private Double capacity;
    private Double usedCapacity;
    private String description;
    private Boolean active;

    public WarehouseResponse() {}

    public WarehouseResponse(Long id, String code, String name, String location, Double capacity, Double usedCapacity, String description, Boolean active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.usedCapacity = usedCapacity;
        this.description = description;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getCapacity() { return capacity; }
    public void setCapacity(Double capacity) { this.capacity = capacity; }

    public Double getUsedCapacity() { return usedCapacity; }
    public void setUsedCapacity(Double usedCapacity) { this.usedCapacity = usedCapacity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
