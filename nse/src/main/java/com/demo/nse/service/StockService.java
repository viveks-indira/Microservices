package com.demo.nse.service;


import com.demo.nse.entity.Stock;
import com.demo.nse.entity.StockResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface StockService {

    public List<StockResponse> getAllStocks();
    public Stock createStock(Stock stock);
    public Stock getStock(Long id);
    public Stock updateStock(Long id , Stock stock);
    public boolean deleteStock(long id);

    // Method to get stocks by a list of IDs
    List<StockResponse> getStocksByIds(List<Long> ids);

}
