package dev.alex96jvm.webstore.service;

import dev.alex96jvm.webstore.dto.ProductDto;
import dev.alex96jvm.webstore.mapper.ProductMapper;
import dev.alex96jvm.webstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;


@Service
@Validated
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productEntityToProductDto)
                .toList();
    }

    public Optional<ProductDto> getProductById(@Positive Long id) {
        return productRepository.findById(id)
                .map(productMapper::productEntityToProductDto);
    }
}
