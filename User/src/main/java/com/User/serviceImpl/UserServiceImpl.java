package com.User.serviceImpl;

import com.User.entity.*;
import com.User.feign.BseClient;
import com.User.feign.NseClient;
import com.User.repository.StockRepository;
import com.User.repository.UserRepository;
import com.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private BseClient bseClient; // Inject Feign Client
    @Autowired
    private NseClient nseClient; // Inject Feign Client

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Map User entities to UserResponse objects
        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(
                        user.getClientId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getCity(),
                        user.getLastTradeDate()
                ))
                .collect(Collectors.toList());

        return userResponses;

    }

    @Override
    public User getUserByClientId(String clientId) {
        return userRepository.findByClientId(clientId);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String clientId, User user) {
        User existingUser = userRepository.findByClientId(clientId);
        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());
        existingUser.setEmail(user.getEmail());
        existingUser.setCity(user.getCity());
        existingUser.setLastTradeDate(user.getLastTradeDate());
        return userRepository.save(existingUser);
    }

    @Override
    public boolean deleteUser(Long clientId) {
        if (userRepository.existsById(clientId)) {
            stockRepository.deleteById(clientId);
            return true; // Stock was found and deleted
        } else {
            return false; // Stock was not found
        }
    }
    @Override
    public List<StockResponse> getAllStocks() {
        List<StockResponse> allStocks = new ArrayList<>();

        try {
            // Fetch stock details from BSE microservice
            List<StockResponse> bseStocks = bseClient.getAllStocks();
            allStocks.addAll(bseStocks.stream()
                    .map(stock -> new StockResponse(stock.getSymbol(), stock.getName(), stock.getPrice(), stock.getDayLow(), stock.getDayHigh(),
                            stock.getYearLow(), stock.getYearHigh(), stock.getMarketCap(), stock.getExchange(), stock.getOpen(), stock.getPrevClose()))
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            // Log the exception and proceed with data from the other service
            System.out.println("BSE service is not available.");
        }

        try {
            // Fetch stock details from NSE microservice
            List<StockResponse> nseStocks = nseClient.getAllStocks();
            allStocks.addAll(nseStocks.stream()
                    .map(stock -> new StockResponse(stock.getSymbol(), stock.getName(), stock.getPrice(), stock.getDayLow(), stock.getDayHigh(),
                            stock.getYearLow(), stock.getYearHigh(), stock.getMarketCap(), stock.getExchange(), stock.getOpen(), stock.getPrevClose()))
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            // Log the exception and proceed with data from the other service
            System.out.println("NSE service is not available.");
        }

        if (allStocks.isEmpty()) {
            throw new RuntimeException("Both NSE and BSE services are not available.");
        }

        return allStocks;
    }

}
