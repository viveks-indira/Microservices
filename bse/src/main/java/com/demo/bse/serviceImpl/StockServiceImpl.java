package com.demo.bse.serviceImpl;


import com.demo.bse.entity.Stock;
import com.demo.bse.entity.StockResponse;
import com.demo.bse.repository.StockRepository;
import com.demo.bse.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        // Convert Stock entities to StockResponse objects
        return stocks.stream()
                .map(stock -> new StockResponse(stock.getSymbol(),stock.getName(),stock.getPrice()
                        ,stock.getDayLow(),stock.getDayHigh(),stock.getYearLow(),stock.getYearHigh(),stock.getMarketCap()
                        ,stock.getExchange(),stock.getOpen(),stock.getPrevClose()))
                .collect(Collectors.toList());
    }
    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock getStock(Long id) {
        // Try to get stock from BSE, if not found, fetch from NSE
        return stockRepository.findById(id).get();
//                .orElseGet(() -> nseClient.getStockFromNse(id));
    }

    @Override
    public Stock updateStock(Long id, Stock stock) {
        stock.setId(id);
        return stockRepository.save(stock);
    }

    @Override
    public boolean deleteStock(long id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return true; // Stock was found and deleted
        } else {
            return false; // Stock was not found
        }
    }

    @Override
    public List<StockResponse> getStocksByIds(List<Long> ids) {
        return stockRepository.findAllById(ids).stream()
                .map(this::convertToStockResponse)
                .collect(Collectors.toList());
    }

    // Helper method to convert Stock to StockResponse
    private StockResponse convertToStockResponse(Stock stock) {
        return new StockResponse();
    }
}
