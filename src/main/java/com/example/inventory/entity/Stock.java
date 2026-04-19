package com.example.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks", uniqueConstraints = @UniqueConstraint(columnNames = {"warehouse_id", "product_id"}))
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long reservedQuantity = 0L;

    @Column(nullable = false)
    private Long minimalStock;

    @Column(nullable = false)
    private Long maximalStock;

    private String location;

    public Stock() {}

    public Stock(Warehouse warehouse, Product product, Long quantity, Long minimalStock, Long maximalStock) {
        this.warehouse = warehouse;
        this.product = product;
        this.quantity = quantity;
        this.minimalStock = minimalStock;
        this.maximalStock = maximalStock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public Long getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(Long reservedQuantity) { this.reservedQuantity = reservedQuantity; }

    public Long getMinimalStock() { return minimalStock; }
    public void setMinimalStock(Long minimalStock) { this.minimalStock = minimalStock; }

    public Long getMaximalStock() { return maximalStock; }
    public void setMaximalStock(Long maximalStock) { this.maximalStock = maximalStock; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Long getAvailableQuantity() {
        return quantity - reservedQuantity;
    }
}
