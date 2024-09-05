package com.User.feign;

import com.User.entity.StockResponse;
import com.User.entity.UserStockResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BseClientFallback implements BseClient {

    @Override
    public List<StockResponse> getStocksByIds(List<Long> ids) {
        return Collections.emptyList(); // Return an empty list or default data
    }

    @Override
    public List<StockResponse> getAllStocks() {
        return Collections.emptyList(); // Return an empty list or default data
    }
}
