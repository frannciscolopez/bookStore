package com.project.bookstore.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.project.bookstore.enums.Status;

@Entity
@Table(name = "order_stock")
public class OrderStock {
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "ordreDate", nullable = false)
    private LocalDateTime orderDate;

    @JoinTable(
        name = "rel_books_orders",
        joinColumns = @JoinColumn(name = "FK_BOOK", nullable = false),
        inverseJoinColumns = @JoinColumn(name="FK_ORDER", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<BookStock> bookStocks;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public List<BookStock> getBookStocks() {
        return bookStocks;
    }
    public void setBookStocks(List<BookStock> bookStocks) {
        this.bookStocks = bookStocks;
    }
    
}
 