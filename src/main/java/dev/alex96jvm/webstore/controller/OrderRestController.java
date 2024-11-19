package dev.alex96jvm.webstore.controller;

import dev.alex96jvm.webstore.dto.OrderDto;
import dev.alex96jvm.webstore.dto.OrderRequestDto;
import dev.alex96jvm.webstore.dto.OrderResponseDto;
import dev.alex96jvm.webstore.dto.OrderUpdateRequestDto;
import dev.alex96jvm.webstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/orders")
public class OrderRestController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<?> getOrders(@RequestParam String customerName) {
        try {
            List<OrderDto> orders = orderService.getOrders(customerName);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        try {
            OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
            return ResponseEntity.ok(orderResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateOrder(@RequestBody OrderUpdateRequestDto orderUpdateRequestDto) {
        try {
            OrderResponseDto orderResponseDto = orderService.updateOrder(orderUpdateRequestDto);
            return ResponseEntity.ok(orderResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }

    @DeleteMapping
    public Boolean deleteOrder(@RequestBody OrderDto orderDto) {
        return orderService.deleteOrder(orderDto);
    }
}
