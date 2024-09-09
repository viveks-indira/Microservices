package com.demo.nse.controller;


import com.demo.nse.entity.Stock;
import com.demo.nse.entity.StockResponse;
import com.demo.nse.serviceImpl.NseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bse")
public class BseStockController {

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
