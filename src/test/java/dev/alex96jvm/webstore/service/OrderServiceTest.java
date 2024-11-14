package dev.alex96jvm.webstore.service;

import dev.alex96jvm.webstore.dto.OrderDto;
import dev.alex96jvm.webstore.dto.OrderRequestDto;
import dev.alex96jvm.webstore.dto.OrderResponseDto;
import dev.alex96jvm.webstore.dto.OrderUpdateRequestDto;
import dev.alex96jvm.webstore.entity.OrderEntity;
import dev.alex96jvm.webstore.entity.ProductEntity;
import dev.alex96jvm.webstore.entity.SaleEntity;
import dev.alex96jvm.webstore.entity.SaleId;
import dev.alex96jvm.webstore.mapper.OrderMapper;
import dev.alex96jvm.webstore.repository.OrderRepository;
import dev.alex96jvm.webstore.repository.ProductRepository;
import dev.alex96jvm.webstore.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private SaleRepository saleRepository;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderService orderService;
    private ProductEntity productEntity;
    private OrderEntity orderEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Bread");
        orderEntity = new OrderEntity("Sidorov");
        orderEntity.setId(UUID.randomUUID());
    }

    @Test
    public void testCreateOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setProductId(1L);
        orderRequestDto.setProductQuantity(2);
        orderRequestDto.setCustomerName("Sidorov");
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(saleRepository.save(any(SaleEntity.class))).thenReturn(new SaleEntity(new SaleId(orderEntity.getId(), productEntity.getId()), 2, productEntity, orderEntity));

        OrderResponseDto result = orderService.createOrder(orderRequestDto);

        assertNotNull(result);
        assertEquals(orderEntity.getId(), result.getOrderId());
        assertEquals(productEntity.getName(), result.getProductName());
        assertEquals(2, result.getProductQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(saleRepository, times(1)).save(any(SaleEntity.class));
    }

    @Test
    public void testUpdateOrder() {
        UUID orderId = orderEntity.getId();
        OrderUpdateRequestDto orderUpdateRequestDto = new OrderUpdateRequestDto();
        orderUpdateRequestDto.setOrderId(orderId);
        orderUpdateRequestDto.setProductId(1L);
        orderUpdateRequestDto.setProductQuantity(3);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(saleRepository.save(any(SaleEntity.class))).thenReturn(new SaleEntity(new SaleId(orderEntity.getId(), productEntity.getId()), 3, productEntity, orderEntity));

        OrderResponseDto result = orderService.updateOrder(orderUpdateRequestDto);

        assertNotNull(result);
        assertEquals(orderEntity.getId(), result.getOrderId());
        assertEquals(productEntity.getName(), result.getProductName());
        assertEquals(3, result.getProductQuantity());
        verify(orderRepository, times(1)).findById(orderId);
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(saleRepository, times(1)).save(any(SaleEntity.class));
    }

    @Test
    public void testDeleteOrder() {
        UUID orderId = orderEntity.getId();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        when(orderRepository.existsById(orderId)).thenReturn(true);

        Boolean result = orderService.deleteOrder(orderDto);

        assertTrue(result);
        verify(orderRepository, times(1)).existsById(orderId);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrder_NotFound() {
        UUID orderId = orderEntity.getId();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        when(orderRepository.existsById(orderId)).thenReturn(false);

        Boolean result = orderService.deleteOrder(orderDto);
        
        assertFalse(result);
        verify(orderRepository, times(1)).existsById(orderId);
        verify(orderRepository, times(0)).deleteById(orderId);
    }
}
