package dev.alex96jvm.webstore.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String customerName;
    private Long productId;
    private Integer productQuantity;
}
