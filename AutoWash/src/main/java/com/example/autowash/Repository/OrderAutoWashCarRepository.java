package com.example.autowash.Repository;

import com.example.autowash.Model.OrderAutoWashCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderAutoWashCarRepository extends JpaRepository<OrderAutoWashCar, Integer> {

    OrderAutoWashCar findOrderAutoWashCarById(Integer orderId);

    List<OrderAutoWashCar> getOrderAutoWashCarsByUserId(Integer userId);
}
