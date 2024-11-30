package dev.alex96jvm.webstore.service;

import dev.alex96jvm.webstore.dto.ProductDto;
import dev.alex96jvm.webstore.entity.ProductEntity;
import dev.alex96jvm.webstore.mapper.ProductMapper;
import dev.alex96jvm.webstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        ProductEntity productEntity = new ProductEntity();
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Meat");
        productDto.setPrice(100.0);
        when(productRepository.findAll()).thenReturn(List.of(productEntity));
        when(productMapper.productEntityToProductDto(any())).thenReturn(productDto);

        List<ProductDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Meat", result.get(0).getName());
        assertEquals(100.0, result.get(0).getPrice());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).productEntityToProductDto(any());
    }

    @Test
    public void testGetProductById_Found() {
        ProductEntity productEntity = new ProductEntity();
        Long productId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName("Bread");
        productDto.setPrice(100.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productMapper.productEntityToProductDto(any())).thenReturn(productDto);

        Optional<ProductDto> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
        assertEquals("Bread", result.get().getName());
        assertEquals(100.0, result.get().getPrice());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).productEntityToProductDto(any());
    }

    @Test
    public void testGetProductById_NotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<ProductDto> result = productService.getProductById(productId);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(0)).productEntityToProductDto(any());
    }
}
