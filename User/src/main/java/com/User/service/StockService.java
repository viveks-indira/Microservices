package com.User.service;

import com.User.entity.*;

import java.util.List;

public interface StockService {

    List<Stock> getAllStock();

    List<Stock> getStockByClientId(String clientId);

    Stock createUserStock(Stock stock);

    Stock updateUserStock(String clientId, Stock stock);

    void deleteUserStock(Long clientId);

    List<StockResponse> getAllStocks();


}
