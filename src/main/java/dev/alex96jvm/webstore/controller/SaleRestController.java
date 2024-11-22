package dev.alex96jvm.webstore.controller;

import dev.alex96jvm.webstore.dto.OrderRequestDto;
import dev.alex96jvm.webstore.dto.SalesResponseDto;
import dev.alex96jvm.webstore.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/sales")
public class SaleRestController {
    private final SaleService saleService;

    @PostMapping("customer")
    public ResponseEntity<List<SalesResponseDto>> getSalesByCustomerName(@RequestBody OrderRequestDto orderRequestDto) {
            List<SalesResponseDto> list = saleService.getSalesByCustomerName(orderRequestDto.getCustomerName());
            return ResponseEntity.ok(list);
    }

    @PostMapping("product")
    public ResponseEntity<List<SalesResponseDto>> getSalesByProductId(@RequestBody OrderRequestDto orderRequestDto) {
            List<SalesResponseDto> list = saleService.getSalesByProductId(orderRequestDto.getProductId());
            return ResponseEntity.ok(list);
    }
}
