package com.User.controller;


import com.User.entity.Stock;
import com.User.entity.StockResponse;
import com.User.entity.User;
import com.User.service.BseClientService;
import com.User.service.StockService;
import com.User.service.UserService;
import com.User.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private UserStockService userStockService;

    @GetMapping("/allStocks")
    public ResponseEntity<?> getAllStocks() {
        try {
            List<StockResponse> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Both NSE and BSE services are not available.");
        }
    }

    @PostMapping("/buy/{clientId}")
    public ResponseEntity<?> buyStock(@PathVariable String clientId, @RequestBody Stock stock) {
        try {
            // Call the service to buy stock and get the associated user
            User user = this.userStockService.buyStock(stock, clientId);

            // Return a 200 OK response with the user in the body
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Return a 404 Not Found response if the client ID is invalid or user not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Return a 500 Internal Server Error for any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while purchasing the stock.");
        }
    }
}
