package com.project.bookstore.service;

import com.project.bookstore.model.OrderStock;
import com.project.bookstore.model.UserOrderRequest;


public interface BookStockService {

    OrderStock makeOrder(UserOrderRequest userOrder);
    
}
