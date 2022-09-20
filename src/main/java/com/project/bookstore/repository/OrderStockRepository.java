package com.project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bookstore.model.OrderStock;

@Repository
public interface OrderStockRepository extends JpaRepository<OrderStock, String> {

}
