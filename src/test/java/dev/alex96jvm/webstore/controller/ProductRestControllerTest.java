package dev.alex96jvm.webstore.controller;

import dev.alex96jvm.webstore.dto.ProductDto;
import dev.alex96jvm.webstore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductRestControllerTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductRestController productRestController;
    private ProductDto productDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Sugar");
        productDto.setPrice(100.0);
    }

    @Test
    public void testGetAllProducts() {
        List<ProductDto> productList = Arrays.asList(productDto);
        when(productService.getAllProducts()).thenReturn(productList);

        ResponseEntity<List<ProductDto>> response = productRestController.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(productDto.getId(), response.getBody().get(0).getId());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById_found() {
        when(productService.getProductById(1L)).thenReturn(Optional.of(productDto));

        ResponseEntity<ProductDto> response = productRestController.getProductById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(productDto.getId(), response.getBody().getId());
        assertEquals(productDto.getName(), response.getBody().getName());
        assertEquals(productDto.getPrice(), response.getBody().getPrice());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testGetProductById_notFound() {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ProductDto> response = productRestController.getProductById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).getProductById(1L);
    }
}
