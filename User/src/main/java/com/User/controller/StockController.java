package com.User.controller;


import com.User.entity.StockResponse;
import com.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private UserService userService;

    @GetMapping("/allStocks")
    public ResponseEntity<?> getAllStocks() {
        try {
            List<StockResponse> stocks = userService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Both NSE and BSE services are not available.");
        }
    }
}
