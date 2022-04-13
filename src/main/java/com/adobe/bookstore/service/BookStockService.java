package com.adobe.bookstore.service;

import com.adobe.bookstore.model.OrderStock;
import com.adobe.bookstore.model.UserOrderRequest;


public interface BookStockService {

    OrderStock makeOrder(UserOrderRequest userOrder);
    
}
