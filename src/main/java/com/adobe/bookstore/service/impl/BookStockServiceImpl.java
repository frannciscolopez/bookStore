package com.adobe.bookstore.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.adobe.bookstore.enums.Status;
import com.adobe.bookstore.mapper.MapperBook;
import com.adobe.bookstore.model.Book;
import com.adobe.bookstore.model.BookStock;
import com.adobe.bookstore.model.OrderStock;
import com.adobe.bookstore.model.UserOrderRequest;
import com.adobe.bookstore.repository.BookStockRepository;
import com.adobe.bookstore.repository.OrderStockRepository;
import com.adobe.bookstore.service.BookStockService;
import com.adobe.bookstore.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookStockServiceImpl implements BookStockService {

    private BookStockRepository bookStockRepository;
    private OrderStockRepository orderStockRepository;
    private Utils utils;
    private MapperBook mapperBook;

    @Autowired
    public BookStockServiceImpl(BookStockRepository bookStockRepository, OrderStockRepository orderStockRepository,
            Utils utils, MapperBook mapperBook) {
        this.bookStockRepository = bookStockRepository;
        this.orderStockRepository = orderStockRepository;
        this.utils = utils;
        this.mapperBook = mapperBook;
    }


    @Override
    public OrderStock makeOrder(UserOrderRequest userOrder) {
        OrderStock orderStock = createNewOrderStock(userOrder);

        if (orderBooksHaveStock(userOrder)) {
            updateAvailableStock(userOrder, orderStock);
            if (orderStock.getStatus().equals(Status.SUCCESFUL)) {
                return orderStock;
            }
            return orderCancelled(orderStock);
        }
        return orderCancelled(orderStock);
    }

    private void updateAvailableStock(UserOrderRequest userOrder, OrderStock orderStock) {
        userOrder.getUserOrder().stream().forEach(book -> updateStock(book, orderStock));
    }

    private void updateStock(Book book, OrderStock orderStock) {
        Optional<BookStock> bookToUpdate = bookStockRepository.findById(book.getId());
        try {
            if (bookToUpdate.isPresent()) {
                if (utils.evaluateQuantityInOrder(bookToUpdate.get().getQuantity(), book.getQuantity())) {
                    updateOrderStatus(orderStock, Status.SUCCESFUL);
                    bookToUpdate.get().setQuantity(bookToUpdate.get().getQuantity() - book.getQuantity());
                    bookStockRepository.save(bookToUpdate.get());
                }
            }
        } catch (Exception e) {
            System.out.println("Update stock failure");
        }
    }

    private boolean orderBooksHaveStock(UserOrderRequest userOrder) {
        return userOrder.getUserOrder().stream().allMatch(predicate -> {
            Optional<BookStock> book = bookStockRepository.findById(predicate.getId());
            if (book.isPresent()) {
                return book.get().getQuantity() > 0;
            }
            return false;
        });
    }

    private OrderStock createNewOrderStock(UserOrderRequest userOrder) {
        OrderStock orderStock = new OrderStock();
        orderStock.setStatus(Status.UNDEFINED);
        orderStock.setId(utils.createOrderStockId());
        orderStock.setBookStocks(mapperBook.mapper(userOrder.getUserOrder()));
        orderStock.setOrderDate(LocalDateTime.now());
        orderStockRepository.save(orderStock);
        return orderStock;
    }

    private OrderStock orderCancelled(OrderStock orderStock) {
        updateOrderStatus(orderStock, Status.CANCELLED);
        return orderStock;
    }

    private void updateOrderStatus(OrderStock orderStock, Status status) {
        OrderStock orderStockToSave = orderStockRepository.getById(orderStock.getId());
        orderStockToSave.setStatus(status);
        orderStockRepository.save(orderStockToSave);
        orderStock.setStatus(status);
    }


}