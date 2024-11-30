package dev.alex96jvm.webstore.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class SalesResponseDto {
    private UUID orderId;
    private String orderDate;
    private String customerName;
    private String productName;
    private Double price;
    private Integer productQuantity;
    private Double totalPrice;
}
