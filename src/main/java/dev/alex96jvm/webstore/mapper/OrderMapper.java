package dev.alex96jvm.webstore.mapper;

import dev.alex96jvm.webstore.dto.OrderDto;
import dev.alex96jvm.webstore.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto orderEntityToOrderDto(OrderEntity orderEntity);
}
