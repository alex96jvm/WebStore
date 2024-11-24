package dev.alex96jvm.webstore.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaleEntity {
    @EmbeddedId
    private SaleId id;
    @Column(name = "product_quantity")
    private Integer productQuantity;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;
}
