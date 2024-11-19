package dev.alex96jvm.webstore.repository;

import dev.alex96jvm.webstore.entity.SaleEntity;
import dev.alex96jvm.webstore.entity.SaleId;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<SaleEntity, SaleId> {
}
