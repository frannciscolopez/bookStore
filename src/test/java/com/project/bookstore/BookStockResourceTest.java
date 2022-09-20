package com.project.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookStock;
import com.project.bookstore.model.UserOrderRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookStockResourceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String bookStockId = "f09387f4-0ec5-4ff4-bc64-3b4bf6108aa1";

    @Test
    @Sql(statements = "INSERT INTO book_stock (id, name, quantity) VALUES ('12345-67890', 'some book', 7)")
    public void shouldReturnCurrentStock() {
        var result = restTemplate.getForObject("http://localhost:" + port + "/books_stock/12345-67890", BookStock.class);
        assertThat(result.getQuantity()).isEqualTo(7);
    }

    @Test
    public void shouldReturnOrderId() {
        UserOrderRequest userOrderStock = mockUserOrderStock();
        var result = restTemplate.postForObject("http://localhost:" + port + "/books_stock/makeOrder", userOrderStock , String.class);
        assertNotNull(result);
    }

    @Test
    public void shouldReturnListOfOrders() {
        UserOrderRequest userOrderStock = mockUserOrderStock();
        restTemplate.postForObject("http://localhost:" + port + "/books_stock/makeOrder", userOrderStock , String.class);
        var result = restTemplate.getForObject("http://localhost:" + port + "/books_stock/orders", List.class);
        assertThat(result.size()).isEqualTo(1);
    }
    
    private UserOrderRequest mockUserOrderStock() {
        UserOrderRequest orderStock = new UserOrderRequest();
        orderStock.setUserOrder(List.of(Book.builder()
                .id(bookStockId)
                .name("enim in commodo consectetur non")
                .quantity(3).build()));
        return orderStock;
    }
}
