package com.User.serviceImpl;

import com.User.entity.*;
import com.User.repository.StockRepository;
import com.User.repository.UserRepository;
import com.User.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStockImpl implements UserStockService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;


    @Override
    public UserWithStocksResponse getUserWithStocks(String clientId) {


        User user = userRepository.findByClientId(clientId);
        if (user == null) {
            throw new RuntimeException("User not found with client ID: " + clientId);
        }

        // Map User entity to UserResponse DTO
        UserResponse userSend=new UserResponse();
        userSend.setClientId(user.getClientId());
        userSend.setName(user.getName());
        userSend.setEmail(user.getEmail());
        userSend.setPhone(user.getPhone());
        userSend.setCity(user.getCity());
        userSend.setLastTradeDate(user.getLastTradeDate());

        // Fetch and map stocks related to the user
        List<Stock> userStocks= stockRepository.findByUserClientId(clientId);

        List<UserStockResponse> stockResponses = userStocks.stream()
                .map(stock -> new UserStockResponse(stock.getSymbol(),stock.getName(),stock.getPrice(),
                        stock.getQuantity(),stock.getExchange()))
                .collect(Collectors.toList());
        return new UserWithStocksResponse(userSend, stockResponses);
    }

    @Override
    public User buyStock(Stock stock,String clientId) {
        User user = userRepository.findByClientId(clientId);
        if (user == null) {
            throw new RuntimeException("User not found with client ID: " + clientId);
        }

        // Set user to the stock
        stock.setUser(user);

        // Save the stock with the associated user
        stockRepository.save(stock);

        return user; // Return the user after successful stock purchase
    }


}
