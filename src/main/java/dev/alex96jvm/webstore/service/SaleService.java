package dev.alex96jvm.webstore.service;

import dev.alex96jvm.webstore.dto.SalesResponseDto;
import dev.alex96jvm.webstore.repository.JdbcTemplateSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class SaleService {
    private final JdbcTemplateSalesRepository jdbcTemplateSalesRepository;

    public List<SalesResponseDto> getSalesByCustomerName(@Size(min = 3, max = 30) String customerName) {
        return jdbcTemplateSalesRepository.getSalesByCustomerName(customerName);
    }

    public List<SalesResponseDto> getSalesByProductId(@Positive Long productId) {
        return jdbcTemplateSalesRepository.getSalesByProductId(productId);
    }

}
