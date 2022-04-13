package com.adobe.bookstore.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.adobe.bookstore.model.Book;
import com.adobe.bookstore.model.BookStock;
import com.adobe.bookstore.repository.BookStockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperBook {

   
    private BookStockRepository bookStockRepository;

    @Autowired
    public MapperBook(BookStockRepository bookStockRepository) {
        this.bookStockRepository = bookStockRepository;
    }

    public List<BookStock> mapper(List<Book> userOrder) {
        List<BookStock> books = new ArrayList();
        userOrder.stream().forEach(
            book -> {
                Optional<BookStock> bookStock = bookStockRepository.findById(book.getId());
                if (bookStock.isPresent()){
                    books.add(bookStock.get());
                }

            }
        );
        return books;
    }
    
}
