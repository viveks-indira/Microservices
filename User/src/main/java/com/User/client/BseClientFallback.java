package com.User.client;

import com.User.entity.StockResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BseClientFallback {

    public List<StockResponse> getAllStocks() {
        return Collections.emptyList(); // Return an empty list or default data
    }
}
