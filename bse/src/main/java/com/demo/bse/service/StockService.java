package com.demo.bse.service;


import com.demo.bse.entity.Stock;
import com.demo.bse.entity.StockResponse;

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
