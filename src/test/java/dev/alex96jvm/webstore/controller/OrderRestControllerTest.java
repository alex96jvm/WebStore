package dev.alex96jvm.webstore.controller;

import dev.alex96jvm.webstore.dto.*;
import dev.alex96jvm.webstore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class OrderRestControllerTest {
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderRestController orderRestController;

    @BeforeEach
    public void setUp() {
        orderService = mock(OrderService.class);
        orderRestController = new OrderRestController(orderService);
    }

    @Test
    public void testGetOrders() {
        String customerName = "Ivanov";
        OrderDto orderDto = new OrderDto();
        orderDto.setId(UUID.randomUUID());
        List<OrderDto> orders = List.of(orderDto);
        when(orderService.getOrders(customerName)).thenReturn(orders);

        ResponseEntity<List<OrderDto>> response = orderRestController.getOrders(customerName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(orderService, times(1)).getOrders(customerName);
    }

    @Test
    public void testCreateOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCustomerName("Ivanov");
        orderRequestDto.setProductId(1L);
        orderRequestDto.setProductQuantity(2);
        OrderResponseDto orderResponseDto = new OrderResponseDto(UUID.randomUUID(), "Product", 2);
        when(orderService.createOrder(orderRequestDto)).thenReturn(orderResponseDto);

        ResponseEntity<OrderResponseDto> response = orderRestController.createOrder(orderRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderResponseDto, response.getBody());
        verify(orderService, times(1)).createOrder(orderRequestDto);
    }

    @Test
    public void testUpdateOrder() {
        OrderUpdateRequestDto orderUpdateRequestDto = new OrderUpdateRequestDto();
        orderUpdateRequestDto.setOrderId(UUID.randomUUID());
        orderUpdateRequestDto.setProductId(1L);
        orderUpdateRequestDto.setProductQuantity(5);
        OrderResponseDto orderResponseDto = new OrderResponseDto(orderUpdateRequestDto.getOrderId(), "Product", 5);
        when(orderService.updateOrder(orderUpdateRequestDto)).thenReturn(orderResponseDto);

        ResponseEntity<OrderResponseDto> response = orderRestController.updateOrder(orderUpdateRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderResponseDto, response.getBody());
        verify(orderService, times(1)).updateOrder(orderUpdateRequestDto);
    }

    @Test
    public void testDeleteOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(UUID.randomUUID());
        when(orderService.deleteOrder(orderDto)).thenReturn(true);

        Boolean result = orderRestController.deleteOrder(orderDto);

        assertTrue(result);
        verify(orderService, times(1)).deleteOrder(orderDto);
    }
}

