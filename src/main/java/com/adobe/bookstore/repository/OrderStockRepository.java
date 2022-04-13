package com.adobe.bookstore.repository;

import com.adobe.bookstore.model.OrderStock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStockRepository extends JpaRepository<OrderStock, String> {

}
