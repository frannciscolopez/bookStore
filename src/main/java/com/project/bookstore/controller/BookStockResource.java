package com.project.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.model.BookStock;
import com.project.bookstore.model.OrderStock;
import com.project.bookstore.model.UserOrderRequest;
import com.project.bookstore.repository.BookStockRepository;
import com.project.bookstore.repository.OrderStockRepository;
import com.project.bookstore.service.BookStockService;

@RestController
@RequestMapping("/books_stock/")
public class BookStockResource {

    private BookStockRepository bookStockRepository;
    private BookStockService bookStockService;
    private OrderStockRepository orderStockRepository;

    @Autowired
    public BookStockResource(BookStockRepository bookStockRepository,
            BookStockService bookStockService, OrderStockRepository orderStockRepository) {
        this.bookStockRepository = bookStockRepository;
        this.bookStockService = bookStockService;
        this.orderStockRepository = orderStockRepository;
    }

    @GetMapping("{bookId}")
    public ResponseEntity<BookStock> getStockById(@PathVariable String bookId) {
        return bookStockRepository.findById(bookId)
                .map(bookStock -> ResponseEntity.ok(bookStock))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/makeOrder")
    public ResponseEntity<String> makeOrder(@RequestBody UserOrderRequest userOrder) {
        return ResponseEntity.ok(bookStockService.makeOrder(userOrder).getId());
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<List<OrderStock>> getOrders() {
        return ResponseEntity.ok(orderStockRepository.findAll());
    }

}
