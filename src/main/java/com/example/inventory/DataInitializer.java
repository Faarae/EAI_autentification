package com.example.inventory;

import com.example.inventory.entity.*;
import com.example.inventory.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void run(String... args) throws Exception {
        if (warehouseRepository.count() == 0) {
            Warehouse wh1 = new Warehouse("WH001", "Warehouse Jakarta", "Jl. Benda No. 1, Jakarta", 10000.0);
            wh1.setDescription("Gudang pusat di Jakarta");
            warehouseRepository.save(wh1);

            Warehouse wh2 = new Warehouse("WH002", "Warehouse Surabaya", "Jl. Raya No. 2, Surabaya", 8000.0);
            wh2.setDescription("Gudang regional di Surabaya");
            warehouseRepository.save(wh2);

            Warehouse wh3 = new Warehouse("WH003", "Warehouse Bandung", "Jl. Gatot No. 3, Bandung", 5000.0);
            wh3.setDescription("Gudang satelit di Bandung");
            warehouseRepository.save(wh3);

            System.out.println("✓ 3 Warehouses berhasil ditambahkan");
        }

        if (productRepository.count() == 0) {
            Product p1 = new Product("SKU001", "Laptop Asus VivoBook", 8000000.0, 2.5, 0.5);
            p1.setDescription("Laptop untuk pekerjaan dan gaming");
            productRepository.save(p1);

            Product p2 = new Product("SKU002", "Mouse Logitech", 150000.0, 0.1, 0.01);
            p2.setDescription("Mouse wireless Logitech");
            productRepository.save(p2);

            Product p3 = new Product("SKU003", "Keyboard Mechanical", 500000.0, 0.8, 0.08);
            p3.setDescription("Keyboard mechanical RGB");
            productRepository.save(p3);

            Product p4 = new Product("SKU004", "Monitor Samsung", 2500000.0, 5.0, 0.15);
            p4.setDescription("Monitor FHD 24 inch");
            productRepository.save(p4);

            System.out.println("✓ 4 Products berhasil ditambahkan");
        }

        if (stockRepository.count() == 0) {
            Warehouse wh1 = warehouseRepository.findAll().get(0);
            Warehouse wh2 = warehouseRepository.findAll().get(1);
            Warehouse wh3 = warehouseRepository.findAll().get(2);

            Product p1 = productRepository.findAll().get(0);
            Product p2 = productRepository.findAll().get(1);
            Product p3 = productRepository.findAll().get(2);
            Product p4 = productRepository.findAll().get(3);

            Stock stock1 = new Stock(wh1, p1, 25L, 5L, 50L);
            stock1.setLocation("A-01-01");
            stockRepository.save(stock1);

            Stock stock2 = new Stock(wh1, p2, 100L, 20L, 200L);
            stock2.setLocation("A-02-01");
            stockRepository.save(stock2);

            Stock stock3 = new Stock(wh2, p3, 60L, 15L, 100L);
            stock3.setLocation("B-01-02");
            stockRepository.save(stock3);

            Stock stock4 = new Stock(wh3, p4, 35L, 10L, 80L);
            stock4.setLocation("C-01-03");
            stockRepository.save(stock4);

            Stock stock5 = new Stock(wh2, p1, 15L, 5L, 50L);
            stock5.setLocation("B-01-01");
            stockRepository.save(stock5);

            System.out.println("✓ 5 Stocks berhasil ditambahkan");
        }

        System.out.println("═══════════════════════════════════════");
        System.out.println("✓ Inventory Data Initialization Selesai!");
        System.out.println("═══════════════════════════════════════");
    }
}
