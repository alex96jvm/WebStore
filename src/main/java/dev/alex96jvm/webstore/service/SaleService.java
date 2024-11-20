package dev.alex96jvm.webstore.service;

import dev.alex96jvm.webstore.dto.SalesResponseDto;
import dev.alex96jvm.webstore.repository.JdbcTemplateSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final JdbcTemplateSalesRepository jdbcTemplateSalesRepository;

    public List<SalesResponseDto> getSalesByCustomerName(String customerName) {
        return jdbcTemplateSalesRepository.getSalesByCustomerName(customerName);
    }

    public List<SalesResponseDto> getSalesByProductId(Long productId) {
        return jdbcTemplateSalesRepository.getSalesByProductId(productId);
    }

}
