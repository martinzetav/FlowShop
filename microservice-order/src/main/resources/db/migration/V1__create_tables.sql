CREATE TABLE orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  date DATETIME,
  status VARCHAR(20),
  total_price DOUBLE
);

CREATE TABLE product_order (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  product_id BIGINT,
  quantity INT,
  price DOUBLE,
  order_id BIGINT,
  CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);