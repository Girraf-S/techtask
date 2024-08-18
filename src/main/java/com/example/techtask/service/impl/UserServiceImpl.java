package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServiceImpl extends UserService, JpaRepository<User, Long> {
    @Query(value = """
            select techtask.users.*
            from techtask.users
            right join techtask.orders
            on techtask.users.id = techtask.orders.user_id
            where extract(year from techtask.orders.created_at) = 2003\s
            and techtask.orders.order_status = 'DELIVERED'
            group by techtask.users.id, techtask.users.email, techtask.users.user_status
            order by sum(techtask.orders.price) desc
            limit 1""", nativeQuery = true)
    User findUser();

    @Query(value = """
            select distinct techtask.users.*
            from techtask.users
            right join techtask.orders
            on techtask.users.id = techtask.orders.user_id
            where extract(year from techtask.orders.created_at) = 2010
            and techtask.orders.order_status = 'PAID'
            """, nativeQuery = true)
    List<User> findUsers();
}