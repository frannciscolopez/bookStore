package com.adobe.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.adobe.bookstore.enums.Status;
import com.adobe.bookstore.mapper.MapperBook;
import com.adobe.bookstore.model.Book;
import com.adobe.bookstore.model.BookStock;
import com.adobe.bookstore.model.OrderStock;
import com.adobe.bookstore.model.UserOrderRequest;
import com.adobe.bookstore.repository.BookStockRepository;
import com.adobe.bookstore.repository.OrderStockRepository;
import com.adobe.bookstore.service.impl.BookStockServiceImpl;
import com.adobe.bookstore.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BookStockServiceTest {

    @InjectMocks
    private BookStockServiceImpl bookStockService;
    @Mock
    private BookStockRepository bookStockRepository;
    @InjectMocks
    private Utils utils;
    @InjectMocks
    private MapperBook mapper;
    @Mock
    private OrderStockRepository orderStockRepository;

    private final String bookStockId = "f09387f4-0ec5-4ff4-bc64-3b4bf6108aa1";

    @Before
    public void setUp() {
        bookStockService = new BookStockServiceImpl(bookStockRepository, orderStockRepository, utils, mapper);
        mapper = new MapperBook(bookStockRepository);
    }

    @Test
    public void orderIsSuccesfulWhenStockIsEnough() {
        UserOrderRequest userOrderStock = mockUserOrderStock();

        when(bookStockRepository.findById(bookStockId))
                .thenReturn(mockBookStock());
        OrderStock order = bookStockService.makeOrder(userOrderStock);
        assertEquals(Status.SUCCESFUL, order.getStatus());
    }

    @Test
    public void orderIsCancelledWhenStockIsNotEnough() {
        UserOrderRequest userOrderStock = mockUserOrderStock();

        when(bookStockRepository.findById(bookStockId))
                .thenReturn(mockBookWithNotEnoughStock());
        OrderStock order = bookStockService.makeOrder(userOrderStock);
        assertEquals(Status.CANCELLED, order.getStatus());
    }

    @Test
    public void orderIsCancelledWhenBookHasNotStock() {
        UserOrderRequest userOrderStock = mockUserOrderStock();

        when(bookStockRepository.findById(bookStockId))
                .thenReturn(mockBookWithouthStock());
        OrderStock order = bookStockService.makeOrder(userOrderStock);
        assertEquals(Status.CANCELLED, order.getStatus());
    }

    private Optional<BookStock> mockBookStock() {
        BookStock bookStock = new BookStock();
        bookStock.setId(bookStockId);
        bookStock.setQuantity(3);
        return Optional.of(bookStock);
    }

    private Optional<BookStock> mockBookWithouthStock() {
        BookStock bookStock = new BookStock();
        bookStock.setId(bookStockId);
        bookStock.setQuantity(2);
        return Optional.of(bookStock);
    }

    private Optional<BookStock> mockBookWithNotEnoughStock() {
        BookStock bookStock = new BookStock();
        bookStock.setId(bookStockId);
        bookStock.setQuantity(2);
        return Optional.of(bookStock);
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
