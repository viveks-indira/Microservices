package com.demo.bse.controller;


import com.demo.bse.entity.Stock;
import com.demo.bse.entity.StockResponse;
import com.demo.bse.serviceImpl.NseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nse")
public class NseStockController {

    @Autowired
    private NseClientService nseClientService;



    @GetMapping("/allStocks")
    public List<StockResponse> getAllStocks(){
        return nseClientService.getAllStocks();
    }

    @GetMapping("/stocks/{id}")
    public Stock getStock(@PathVariable Long id) {
        return nseClientService.getStockFromNse(id);
    }

    @PostMapping("/stocks/{id}/buy")
    public Stock buyStock(@PathVariable Long id, @RequestParam int quantity) {
        return nseClientService.buyStockFromNse(id, quantity);
    }

    @PostMapping("/stocks/{id}/sell")
    public Stock sellStock(@PathVariable Long id, @RequestParam int quantity) {
        return nseClientService.sellStockToNse(id, quantity);
    }
}
