package com.demo.bse.controller;


import com.demo.bse.entity.Stock;
import com.demo.bse.entity.StockResponse;
import com.demo.bse.service.StockService;
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

    @GetMapping("/allStocks")
    public List<StockResponse> getAllStocks(){
        return stockService.getAllStocks();
    }

    @PostMapping("/listStock")
    public ResponseEntity<String> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        if (createdStock != null) {
            return ResponseEntity.ok("Stock is listed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to list stock.");
        }
    }


    @GetMapping("/{id}")
    public Stock getStock(@PathVariable Long id) {
        return stockService.getStock(id);
    }

    @PutMapping("/{id}")
    public Stock updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        return stockService.updateStock(id, stock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable Long id) {
        boolean isDeleted = stockService.deleteStock(id);
        if (isDeleted) {
            return ResponseEntity.ok("Stock removed from NSE successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found or could not be removed.");
        }
    }


    @GetMapping("/batch")
    public List<StockResponse> getStocksByIds(@RequestParam List<Long> ids) {
        return stockService.getStocksByIds(ids);
    }


}
