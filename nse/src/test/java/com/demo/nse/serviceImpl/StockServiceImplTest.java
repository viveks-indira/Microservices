package com.demo.nse.serviceImpl;

import com.demo.nse.entity.Stock;
import com.demo.nse.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStocks() {
        // Given
        Stock stock1 = new Stock(1L, "AAPL", "Apple", 150.0, 145.0, 155.0, 120.0, 200.0, 2_500_000_000.0, "NASDAQ", 149.0, 148.0);
        Stock stock2 = new Stock(2L, "GOOGL", "Google", 2800.0, 2700.0, 2900.0, 2000.0, 3000.0, 1_800_000_000.0, "NASDAQ", 2785.0, 2780.0);
        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        // When
        var result = stockService.getAllStocks();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("AAPL", result.get(0).getSymbol());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void createStock() {
        // Given
        Stock stock = new Stock(null, "MSFT", "Microsoft", 300.0, 295.0, 305.0, 250.0, 400.0, 2_100_000_000.0, "NASDAQ", 299.0, 298.0);
        Stock savedStock = new Stock(3L, "MSFT", "Microsoft", 300.0, 295.0, 305.0, 250.0, 400.0, 2_100_000_000.0, "NASDAQ", 299.0, 298.0);
        when(stockRepository.save(stock)).thenReturn(savedStock);

        // When
        Stock result = stockService.createStock(stock);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("MSFT", result.getSymbol());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void getStock() {
        // Given
        Stock stock = new Stock(1L, "AAPL", "Apple", 150.0, 145.0, 155.0, 120.0, 200.0, 2_500_000_000.0, "NASDAQ", 149.0, 148.0);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        // When
        Stock result = stockService.getStock(1L);

        // Then
        assertNotNull(result);
        assertEquals("AAPL", result.getSymbol());
        assertEquals("Apple", result.getName());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void updateStock() {
        // Given
        Stock existingStock = new Stock(1L, "AAPL", "Apple", 150.0, 145.0, 155.0, 120.0, 200.0, 2_500_000_000.0, "NASDAQ", 149.0, 148.0);
        Stock updatedStock = new Stock(1L, "AAPL", "Apple", 160.0, 150.0, 165.0, 120.0, 200.0, 2_500_000_000.0, "NASDAQ", 159.0, 158.0);

        // Mocking findById to return the existingStock
        when(stockRepository.findById(1L)).thenReturn(Optional.of(existingStock));

        // Mocking save to return the updatedStock
        when(stockRepository.save(existingStock)).thenReturn(updatedStock);

        // When
        Stock result = stockService.updateStock(1L, updatedStock);

        // Then
        assertNotNull(result);  // Ensure the result is not null
        assertEquals(160.0, result.getPrice());  // Verify that the price was updated correctly
        assertEquals(150.0, result.getDayLow());  // Verify that the dayLow was updated correctly
        verify(stockRepository, times(1)).findById(1L);  // Verify that findById was called once
        verify(stockRepository, times(1)).save(existingStock);  // Verify that save was called once
    }



    @Test
    void deleteStock() {
        // Given
        Long stockId = 1L;

        // Mock the existsById() to return true, indicating the stock exists
        when(stockRepository.existsById(stockId)).thenReturn(true);

        // Mock deleteById to do nothing (since it doesn't return anything)
        doNothing().when(stockRepository).deleteById(stockId);

        // When
        stockService.deleteStock(stockId);

        // Then
        verify(stockRepository, times(1)).existsById(stockId);  // Ensure existsById was called
        verify(stockRepository, times(1)).deleteById(stockId);  // Ensure deleteById was called
    }



}
