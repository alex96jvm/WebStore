package dev.alex96jvm.webstore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class OrderDto {
    @NotNull
    private UUID id;
}
