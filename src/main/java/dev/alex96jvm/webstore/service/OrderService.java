package dev.alex96jvm.webstore.service;

import dev.alex96jvm.webstore.dto.OrderDto;
import dev.alex96jvm.webstore.dto.OrderRequestDto;
import dev.alex96jvm.webstore.dto.OrderResponseDto;
import dev.alex96jvm.webstore.dto.OrderUpdateRequestDto;
import dev.alex96jvm.webstore.entity.OrderEntity;
import dev.alex96jvm.webstore.entity.ProductEntity;
import dev.alex96jvm.webstore.entity.SaleEntity;
import dev.alex96jvm.webstore.entity.SaleId;
import dev.alex96jvm.webstore.exception.OrderNotFoundException;
import dev.alex96jvm.webstore.mapper.OrderMapper;
import dev.alex96jvm.webstore.repository.OrderRepository;
import dev.alex96jvm.webstore.repository.ProductRepository;
import dev.alex96jvm.webstore.repository.SaleRepository;
import dev.alex96jvm.webstore.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final SaleRepository saleRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getOrders(@Size(min = 3, max = 30) String customerName) {
        return orderRepository.findByCustomerName(customerName).stream()
                .map(orderMapper::orderEntityToOrderDto)
                .toList();
    }

    @Transactional
    public OrderResponseDto createOrder (@Valid OrderRequestDto orderRequestDto) {
        Long productId = orderRequestDto.getProductId();
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        OrderEntity orderEntity = orderRepository.save(
                new OrderEntity(orderRequestDto.getCustomerName())
        );

        SaleId saleId = new SaleId(orderEntity.getId(), productEntity.getId());
        SaleEntity saleEntity = new SaleEntity(saleId, orderRequestDto.getProductQuantity(), productEntity, orderEntity);
        saleRepository.save(saleEntity);
        return new OrderResponseDto(orderEntity.getId(), productEntity.getName(), saleEntity.getProductQuantity());
    }

    @Transactional
    public OrderResponseDto updateOrder(@Valid OrderUpdateRequestDto orderUpdateRequestDto) {
        UUID orderId = orderUpdateRequestDto.getOrderId();
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        orderEntity.setOrderDate(LocalDate.now());
        orderRepository.save(orderEntity);

        Long productId = orderUpdateRequestDto.getProductId();
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        SaleId saleId = new SaleId(orderEntity.getId(), productEntity.getId());
        SaleEntity saleEntity = new SaleEntity(saleId, orderUpdateRequestDto.getProductQuantity(), productEntity, orderEntity);
        saleRepository.save(saleEntity);
        return new OrderResponseDto(orderEntity.getId(), productEntity.getName(), saleEntity.getProductQuantity());
    }

    public Boolean deleteOrder(@Valid OrderDto orderDto) {
        UUID id = orderDto.getId();
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
