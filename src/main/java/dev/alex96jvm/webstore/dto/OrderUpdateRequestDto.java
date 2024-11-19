package dev.alex96jvm.webstore.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderUpdateRequestDto {
    private UUID orderId;
    private Long productId;
    private Integer productQuantity;
}
