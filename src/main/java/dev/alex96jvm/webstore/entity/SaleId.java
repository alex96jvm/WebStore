package dev.alex96jvm.webstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaleId implements Serializable {
    @Column(name = "order_id")
    private UUID orderId;
    @Column(name = "product_id")
    private Long productId;
}
