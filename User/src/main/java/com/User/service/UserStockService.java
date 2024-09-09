package com.User.service;

import com.User.entity.*;

public interface UserStockService {

    UserWithStocksResponse getUserWithStocks(String clientId);

    User buyStock(Stock stock, String clientId);


}
