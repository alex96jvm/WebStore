package dev.alex96jvm.webstore.repository;

import dev.alex96jvm.webstore.dto.SalesResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JdbcTemplateSalesRepositoryTest {
    private JdbcTemplate jdbcTemplate;
    private JdbcTemplateSalesRepository jdbcTemplateSalesRepository;

    @BeforeEach
    public void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername("sa");
        dataSource.setPassword("password");
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplateSalesRepository = new JdbcTemplateSalesRepository(jdbcTemplate);
        createSchemaAndTestData();
    }

    private void createSchemaAndTestData() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS sale");
        jdbcTemplate.execute("DROP TABLE IF EXISTS product");
        jdbcTemplate.execute("DROP TABLE IF EXISTS order");
        jdbcTemplate.execute("CREATE TABLE order (id UUID PRIMARY KEY, order_date DATE, customer_name VARCHAR(255))");
        jdbcTemplate.execute("CREATE TABLE product (id LONG PRIMARY KEY, name VARCHAR(255), price DOUBLE)");
        jdbcTemplate.execute("CREATE TABLE sale (order_id UUID, product_id LONG, product_quantity INT, PRIMARY KEY (order_id, product_id))");
        jdbcTemplate.execute("INSERT INTO sale (order_id, product_id, product_quantity) VALUES ('a34f9d9e-0544-48b0-b607-ff52be77a249', 1, 3)");
        jdbcTemplate.execute("INSERT INTO sale (order_id, product_id, product_quantity) VALUES ('b13c1b70-29f6-4746-b8c2-e9c3d209f6e1', 2, 1)");
        jdbcTemplate.execute("INSERT INTO order (id, order_date, customer_name) VALUES ('a34f9d9e-0544-48b0-b607-ff52be77a249', '2024-11-22', 'Ivanov')");
        jdbcTemplate.execute("INSERT INTO order (id, order_date, customer_name) VALUES ('b13c1b70-29f6-4746-b8c2-e9c3d209f6e1', '2024-11-23', 'Petrov')");
        jdbcTemplate.execute("INSERT INTO product (id, name, price) VALUES (1, 'Sugar', 100.0)");
        jdbcTemplate.execute("INSERT INTO product (id, name, price) VALUES (2, 'Bread', 200.0)");

    }

    @Test
    public void testGetSalesByCustomerName() {
        String customerName = "Ivanov";
        List<SalesResponseDto> sales = jdbcTemplateSalesRepository.getSalesByCustomerName(customerName);

        assertNotNull(sales);
        assertFalse(sales.isEmpty());
        assertEquals("Ivanov", sales.get(0).getCustomerName());
        assertEquals("Sugar", sales.get(0).getProductName());
        assertEquals(300.0, sales.get(0).getTotalPrice());
    }

    @Test
    public void testGetSalesByProductId() {
        Long productId = 1L;
        List<SalesResponseDto> sales = jdbcTemplateSalesRepository.getSalesByProductId(productId);

        assertNotNull(sales);
        assertFalse(sales.isEmpty());
        assertEquals("Sugar", sales.get(0).getProductName());
        assertEquals(300.0, sales.get(0).getTotalPrice());
    }
}

