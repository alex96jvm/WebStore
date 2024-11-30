package dev.alex96jvm.webstore.repository;

import dev.alex96jvm.webstore.dto.SalesResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcTemplateSalesRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateSalesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SalesResponseDto> getSalesByCustomerName(String customerName) {
        String sql = """
                SELECT
                    o.id AS order_id,
                    o.order_date,
                    o.customer_name,
                    p.name AS product_name,
                    p.price,
                    s.product_quantity,
                    (p.price * s.product_quantity) AS total_price
                FROM
                    sale s
                JOIN
                    product p ON s.product_id = p.id
                JOIN
                    order o ON s.order_id = o.id
                WHERE
                    o.customer_name = ?;
                """;
        return jdbcTemplate.query(sql, new Object[]{customerName}, this::mapToSalesResponse);
    }

    public List<SalesResponseDto> getSalesByProductId(Long productId) {
        String sql = """
                SELECT
                    o.id AS order_id,
                    o.order_date,
                    o.customer_name,
                    p.name AS product_name,
                    p.price,
                    s.product_quantity,
                    (p.price * s.product_quantity) AS total_price
                FROM
                    sale s
                JOIN
                    product p ON s.product_id = p.id
                JOIN
                    order o ON s.order_id = o.id
                WHERE
                    s.product_id = ?;
                """;
        return jdbcTemplate.query(sql, new Object[]{productId}, this::mapToSalesResponse);
    }

    private SalesResponseDto mapToSalesResponse(ResultSet rs, int rowNum) throws SQLException {
        SalesResponseDto response = new SalesResponseDto();
        response.setOrderId(UUID.fromString(rs.getString("order_id")));
        response.setOrderDate(rs.getString("order_date"));
        response.setCustomerName(rs.getString("customer_name"));
        response.setProductName(rs.getString("product_name"));
        response.setPrice(rs.getDouble("price"));
        response.setProductQuantity(rs.getInt("product_quantity"));
        response.setTotalPrice(rs.getDouble("total_price"));
        return response;
    }
}
