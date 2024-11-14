package dev.alex96jvm.webstore.mapper;

import dev.alex96jvm.webstore.dto.ProductDto;
import dev.alex96jvm.webstore.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productEntityToProductDto(ProductEntity productEntity);
}
