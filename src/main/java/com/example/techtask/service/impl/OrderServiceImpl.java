package com.example.techtask.service.impl;

import com.example.techtask.model.Order;
import com.example.techtask.service.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderServiceImpl extends OrderService, JpaRepository<Order, Long> {
    @Override
    @Query(value = """
            select * from techtask.orders\s
            where techtask.orders.quantity > 1
            order by techtask.orders.created_at desc
            limit 1""", nativeQuery = true)
    Order findOrder();
    @Override
    @Query(value = """
            select techtask.orders.*
            from techtask.users
            right join techtask.orders
            on techtask.users.id = techtask.orders.user_id
            where techtask.users.user_status = 'ACTIVE'
            order by techtask.orders.created_at""", nativeQuery = true)
    List<Order> findOrders();
}
