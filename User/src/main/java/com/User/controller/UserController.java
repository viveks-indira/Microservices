package com.User.controller;

import com.User.entity.StockResponse;
import com.User.entity.User;
import com.User.entity.UserResponse;
import com.User.entity.UserWithStocksResponse;
import com.User.service.UserService;
import com.User.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStockService userStockService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();

            if (users.isEmpty()) {
                // If no users are found, return 204 No Content
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found.");
            } else {
                // If users are found, return 200 OK with the list of users
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error retrieving users: " + e.getMessage());
            // Return 500 Internal Server Error in case of an exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving users: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String clientId) {
        try {
            User user = userService.getUserByClientId(clientId);

            if (user != null) {
                // If user is found, return 200 OK with the user details
                return ResponseEntity.ok(user);
            } else {
                // If user is not found, return 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with client ID: " + clientId);
            }
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error retrieving user: " + e.getMessage());
            // Return 500 Internal Server Error in case of an exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the user: " + e.getMessage());
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        System.out.println("Creating user: " + user.getName()); // Log the user's name being created
        try {
            // Encode the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Create the user
            User createdUser = userService.createUser(user);

            // Return 201 Created with the created user
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error creating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the user: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> UpdateUser(@PathVariable("id") String clientId, @RequestBody Map<String, Object> updates) {
        try {
            UserResponse updatedUser = userService.updateUser(clientId, updates);
            return ResponseEntity.ok(updatedUser); // Return 200 OK with the updated user

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Return 400 Bad Request
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the user."); // Return 500 Internal Server Error
        }
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable("id") String clientId, @RequestBody User user) {
//        try {
//            // Attempt to update the user
//            User updatedUser = userService.updateUser(clientId, user);
//
//            // If successful, return 200 OK with the updated user
//            return ResponseEntity.ok(updatedUser);
//        } catch (RuntimeException e) {
//            // If the user is not found or any other runtime error occurs, return 404 Not Found
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with client ID: " + clientId);
//        } catch (Exception e) {
//            // If any other exception occurs, return 500 Internal Server Error
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while updating the user.");
//        }
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long clientId) {
        boolean isDeleted = userService.deleteUser(clientId);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or could not be deleted.");
        }
    }

    @GetMapping("/{id}/withStocks")

    public ResponseEntity<?> getUserWithStocks(@PathVariable("id")  String clientId) {
        try {
            UserWithStocksResponse response = userStockService.getUserWithStocks(clientId);
            return ResponseEntity.ok(response);  // Return 200 OK with the response DTO
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());  // Return 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching user and stocks.");  // Return 500 Internal Server Error
        }
    }



}
