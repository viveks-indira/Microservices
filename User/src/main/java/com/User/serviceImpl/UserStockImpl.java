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
    public User getUserByClientId(String clientId) {
        return userRepository.findByClientId(clientId);
    }

    @Override
    public UserWithStocksResponse getUserWithStocks(String clientId) {
        User user = getUserByClientId(clientId);
        UserResponse userSend=new UserResponse();
        userSend.setClientId(user.getClientId());
        userSend.setName(user.getName());
        userSend.setEmail(user.getEmail());
        userSend.setPhone(user.getPhone());
        userSend.setCity(user.getCity());
        userSend.setLastTradeDate(user.getLastTradeDate());

        List<Stock> userStocks= stockRepository.findByUserClientId(clientId);

        List<UserStockResponse> stockResponses = userStocks.stream()
                .map(stock -> new UserStockResponse(stock.getSymbol(),stock.getName(),stock.getPrice(),
                        stock.getQuantity(),stock.getExchange()))
                .collect(Collectors.toList());
        return new UserWithStocksResponse(userSend, stockResponses);
    }


}
