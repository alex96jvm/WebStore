package dev.alex96jvm.webstore.repository;

import dev.alex96jvm.webstore.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<OrderEntity, UUID> {
    List<OrderEntity> findByCustomerName(String customerName);
}
