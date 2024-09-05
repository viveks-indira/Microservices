package com.User.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserWithStocksResponse {
    private UserResponse userDetails;
    private List<UserStockResponse> stocksHold; // List of stock details
}
