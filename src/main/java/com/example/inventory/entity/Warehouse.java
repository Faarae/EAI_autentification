package com.example.inventory.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double capacity;

    @Column(nullable = false)
    private Double usedCapacity = 0.0;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean active = true;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockMovement> stockMovements;

    public Warehouse() {}

    public Warehouse(String code, String name, String location, Double capacity) {
        this.code = code;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
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

    public List<Stock> getStocks() { return stocks; }
    public void setStocks(List<Stock> stocks) { this.stocks = stocks; }

    public List<StockMovement> getStockMovements() { return stockMovements; }
    public void setStockMovements(List<StockMovement> stockMovements) { this.stockMovements = stockMovements; }
}
