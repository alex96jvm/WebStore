package dev.alex96jvm.webstore.controller;

import dev.alex96jvm.webstore.dto.OrderRequestDto;
import dev.alex96jvm.webstore.dto.SalesResponseDto;
import dev.alex96jvm.webstore.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaleRestControllerTest {
    @Mock
    private SaleService saleService;
    @InjectMocks
    private SaleRestController saleRestController;

    @BeforeEach
    public void setUp() {
        saleService = mock(SaleService.class);
        saleRestController = new SaleRestController(saleService);
    }

    @Test
    public void testGetSalesByCustomerName() {
        String customerName = "Petrov";
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
        when(saleService.getSalesByCustomerName(customerName)).thenReturn(salesResponseList);

        ResponseEntity<List<SalesResponseDto>> response = saleRestController.getSalesByCustomerName(orderRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Sugar", response.getBody().get(0).getProductName());
        verify(saleService, times(1)).getSalesByCustomerName(customerName);
    }

    @Test
    public void testGetSalesByProductId() {
        Long productId = 1L;
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setProductId(productId);
        SalesResponseDto salesResponseDto = new SalesResponseDto();
        salesResponseDto.setOrderId(UUID.randomUUID());
        salesResponseDto.setCustomerName("Sidorov");
        salesResponseDto.setProductName("Meat");
        salesResponseDto.setPrice(100.0);
        salesResponseDto.setProductQuantity(2);
        salesResponseDto.setTotalPrice(200.0);
        List<SalesResponseDto> salesResponseList = List.of(salesResponseDto);
        when(saleService.getSalesByProductId(productId)).thenReturn(salesResponseList);

        ResponseEntity<List<SalesResponseDto>> response = saleRestController.getSalesByProductId(orderRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Meat", response.getBody().get(0).getProductName());
        verify(saleService, times(1)).getSalesByProductId(productId);
    }
}
