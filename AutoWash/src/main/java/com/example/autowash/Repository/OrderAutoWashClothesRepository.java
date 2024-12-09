package com.example.autowash.Repository;

import com.example.autowash.Model.OrderAutoWashCar;
import com.example.autowash.Model.OrderAutoWashClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderAutoWashClothesRepository extends JpaRepository<OrderAutoWashClothes,Integer> {

    OrderAutoWashClothes findOrderAutoWashClothesById(Integer orderId);

    List<OrderAutoWashClothes> getOrderAutoWashClothesByUserId(Integer userId);
}
