CREATE TABLE sale (
    order_id UUID NOT NULL,
    product_id BIGINT NOT NULL,
    product_quantity INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_sale_order FOREIGN KEY (order_id) REFERENCES "order" (id) ON DELETE CASCADE,
    CONSTRAINT fk_sale_product FOREIGN KEY (product_id) REFERENCES product (id)
);
