package dev.alex96jvm.webstore.service;

import dev.alex96jvm.webstore.dto.SalesResponseDto;
import dev.alex96jvm.webstore.dto.OrderRequestDto;
import dev.alex96jvm.webstore.repository.JdbcTemplateSalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaleServiceTest {
    @Mock
    private JdbcTemplateSalesRepository jdbcTemplateSalesRepository;
    @InjectMocks
    private SaleService saleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSalesByCustomerName() {
        String customerName = "Ivanov";
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCustomerName(customerName);
        SalesResponseDto salesResponseDto = new SalesResponseDto();
        salesResponseDto.setOrderId(UUID.randomUUID());
        salesResponseDto.setCustomerName(customerName);
        salesResponseDto.setProductName("Sugar");
        salesResponseDto.setPrice(100.0);
        salesResponseDto.setProductQuantity(2);
        salesResponseDto.setTotalPrice(200.0);
        List<SalesResponseDto> salesResponseList = List.of(salesResponseDto);
        when(jdbcTemplateSalesRepository.getSalesByCustomerName(customerName)).thenReturn(salesResponseList);

        List<SalesResponseDto> result = saleService.getSalesByCustomerName(customerName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sugar", result.get(0).getProductName());
        verify(jdbcTemplateSalesRepository, times(1)).getSalesByCustomerName(customerName);
    }

    @Test
    public void testGetSalesByProductId() {
        Long productId = 1L;
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setProductId(productId);
        SalesResponseDto salesResponseDto = new SalesResponseDto();
        salesResponseDto.setOrderId(UUID.randomUUID());
        salesResponseDto.setCustomerName("Petrov");
        salesResponseDto.setProductName("Meat");
        salesResponseDto.setPrice(100.0);
        salesResponseDto.setProductQuantity(2);
        salesResponseDto.setTotalPrice(200.0);
        List<SalesResponseDto> salesResponseList = List.of(salesResponseDto);
        when(jdbcTemplateSalesRepository.getSalesByProductId(productId)).thenReturn(salesResponseList);

        List<SalesResponseDto> result = saleService.getSalesByProductId(productId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Meat", result.get(0).getProductName());
        verify(jdbcTemplateSalesRepository, times(1)).getSalesByProductId(productId);
    }
}

