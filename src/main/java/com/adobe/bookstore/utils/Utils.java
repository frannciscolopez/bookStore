package com.adobe.bookstore.utils;

import org.springframework.stereotype.Service;

@Service
public class Utils {

    public String createOrderStockId() {
        Double randomId = ((Math.random() * (1000000000 - 2000000000)) + 2000000000);
        return randomId.toString();
    }

    public boolean evaluateQuantityInOrder(Integer quantity, Integer quantity2) {
        return (quantity - quantity2) >= 0;
    }
    
}
