package com.User.service;

import com.User.entity.User;
import com.User.entity.UserWithStocksResponse;

public interface UserStockService {

    User getUserByClientId(String clientId);
    // Method to get user details along with their stocks
    UserWithStocksResponse getUserWithStocks(String clientId);



}
