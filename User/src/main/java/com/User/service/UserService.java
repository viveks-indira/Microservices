package com.User.service;

import com.User.entity.User;
import com.User.entity.StockResponse;
import com.User.entity.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserResponse> getAllUsers();

    User getUserByClientId(String clientId);


    User createUser(User user);

    UserResponse updateUser(String clientId, Map<String, Object> updates);

    boolean deleteUser(Long clientId);

    List<StockResponse> getAllStocks();


}
