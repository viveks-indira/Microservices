package com.demo.nse.feign;

import com.demo.nse.entity.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bse", url = "http://localhost:7070")
public interface BseClient {

    @GetMapping("/api/stocks/{id}")
    Stock getStockFromBse(@PathVariable("id") Long id);

    @PostMapping("/api/stocks/{id}/buy")
    Stock buyStockFromBse(@PathVariable("id") Long id, @RequestParam int quantity);

    @PostMapping("/api/stocks/{id}/sell")
    Stock sellStockToBse(@PathVariable("id") Long id, @RequestParam int quantity);
}
