package com.example.oro.repository;

import com.example.oro.Entity.Order;
import com.example.oro.Entity.Part;
import com.example.oro.Entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByLineItems_Part(Part part1);

    List<Order> findByBuyer(UserModel user1);
}
