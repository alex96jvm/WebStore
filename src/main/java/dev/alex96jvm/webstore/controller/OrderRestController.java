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
    public ResponseEntity<List<OrderDto>> getOrders(@RequestParam String customerName) {
        return ResponseEntity.ok(orderService.getOrders(customerName));
    }

    @PostMapping()
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDto));
    }

    @PutMapping()
    public ResponseEntity<OrderResponseDto> updateOrder(@RequestBody OrderUpdateRequestDto orderUpdateRequestDto) {
        return ResponseEntity.ok(orderService.updateOrder(orderUpdateRequestDto));
    }

    @DeleteMapping()
    public Boolean deleteOrder(@RequestBody OrderDto orderDto) {
        return orderService.deleteOrder(orderDto);
    }
}
