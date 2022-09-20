package com.project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bookstore.model.BookStock;

@Repository
public interface BookStockRepository extends JpaRepository<BookStock, String> {

}
