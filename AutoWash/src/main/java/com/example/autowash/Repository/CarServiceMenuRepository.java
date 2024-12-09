package com.example.autowash.Repository;

import com.example.autowash.Model.CarServiceMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarServiceMenuRepository extends JpaRepository<CarServiceMenu, Integer> {

    CarServiceMenu findCarServiceMenuById(Integer id);

    List<CarServiceMenu> getCarServiceMenuByAutoWashCarId(Integer id);

}
