package dev.alex96jvm.webstore.dto;

import lombok.Data;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class OrderRequestDto {
    @Size(min = 3, max = 30, message = "Name should be between 2 and 30 characters")
    private String customerName;
    @Positive
    private Long productId;
    @Positive
    private Integer productQuantity;
}
