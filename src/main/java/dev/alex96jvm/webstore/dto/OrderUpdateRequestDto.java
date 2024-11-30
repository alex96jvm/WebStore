package dev.alex96jvm.webstore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
public class OrderUpdateRequestDto {
    @NotNull
    private UUID orderId;
    @Positive
    private Long productId;
    @Positive
    private Integer productQuantity;
}
