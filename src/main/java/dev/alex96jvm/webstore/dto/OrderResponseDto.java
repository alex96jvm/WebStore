package dev.alex96jvm.webstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderResponseDto {
    private UUID orderId;
    private String productName;
    private Integer productQuantity;
}
