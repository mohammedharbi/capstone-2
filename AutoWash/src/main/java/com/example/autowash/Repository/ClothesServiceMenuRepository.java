package com.example.autowash.Repository;

import com.example.autowash.Model.CarServiceMenu;
import com.example.autowash.Model.ClothesServiceMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesServiceMenuRepository extends JpaRepository<ClothesServiceMenu, Integer> {

    ClothesServiceMenu findClothesServiceMenuById(Integer id);

    List<ClothesServiceMenu> getClothesServiceMenuByAutoWashClothesId(Integer id);
}
