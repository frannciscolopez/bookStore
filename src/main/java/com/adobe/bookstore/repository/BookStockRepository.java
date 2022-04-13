package com.adobe.bookstore.repository;

import com.adobe.bookstore.model.BookStock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStockRepository extends JpaRepository<BookStock, String> {

}
