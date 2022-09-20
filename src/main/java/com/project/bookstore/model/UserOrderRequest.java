package com.project.bookstore.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class UserOrderRequest {

    private List<Book> userOrder;

    public List<Book> getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(List<Book> userOrder) {
        this.userOrder = userOrder;
    }
    
}