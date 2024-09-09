package com.User.serviceImpl;

import com.User.entity.*;
import com.User.client.BseClient;
import com.User.repository.StockRepository;
import com.User.repository.UserRepository;
import com.User.service.BseClientService;
import com.User.service.NseClientService;
import com.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private BseClientService bseClientService; // Inject Feign Client
    @Autowired
    private NseClientService nseClientService; // Inject Feign Client

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

//    @Override
//    public User updateUser(String clientId, User user) {
//        User existingUser = userRepository.findByClientId(clientId);
//        existingUser.setName(user.getName());
//        existingUser.setPhone(user.getPhone());
//        existingUser.setEmail(user.getEmail());
//        existingUser.setCity(user.getCity());
//        existingUser.setLastTradeDate(user.getLastTradeDate());
//        return userRepository.save(existingUser);
//    }

    @Override
    public UserResponse updateUser(String clientId, Map<String, Object> updates) {
        User existingUser = userRepository.findByClientId(clientId);
        if (existingUser == null) {
            throw new RuntimeException("User not found with client ID: " + clientId);
        }

        // Apply updates dynamically
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingUser.setName((String) value);
                    break;
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                case "phone":
                    existingUser.setPhone((String) value);
                    break;
                case "city":
                    existingUser.setCity((String) value);
                    break;
                // Add more fields as necessary
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Save the updated user
        User updatedUser = this.userRepository.save(existingUser);
        UserResponse userResponse=new UserResponse(updatedUser.getClientId(),updatedUser.getName(),updatedUser.getEmail(),
                updatedUser.getPhone(),updatedUser.getCity(),updatedUser.getLastTradeDate());
        return userResponse;
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
            Flux<StockResponse> bseStocksFlux = bseClientService.getAllStocks();
            System.out.println("ALl Stocks " + bseStocksFlux);
            bseStocksFlux.subscribe(
                    stock -> allStocks.add(stock), // On next
                    e -> {
                        // Handle error
                        System.out.println("BSE service is not available: " + e.getMessage());
                        // Optionally, fetch data from another service here
                    },
                    () -> {
                        // Complete
                        System.out.println("Finished fetching BSE stocks.");
                    }
            );

        } catch (Exception e) {
            // Log the exception and proceed with data from the other service
            System.out.println("BSE service is not available.");
        }

        try {
            // Fetch stock details from NSE microservice
            Flux<StockResponse> nseStocksFlux = nseClientService.getAllStocks();
            System.out.println("ALl Stocks " + nseStocksFlux);
            nseStocksFlux.subscribe(
                    stock -> allStocks.add(stock), // On next
                    e -> {
                        // Handle error
                        System.out.println("NSE service is not available: " + e.getMessage());
                        // Optionally, fetch data from another service here
                    },
                    () -> {
                        // Complete
                        System.out.println("Finished fetching NSE stocks.");
                    }
            );

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
