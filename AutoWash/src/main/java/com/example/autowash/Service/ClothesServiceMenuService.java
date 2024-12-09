package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.*;
import com.example.autowash.Repository.AutoWashClothesRepository;
import com.example.autowash.Repository.ClothesServiceMenuRepository;
import com.example.autowash.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothesServiceMenuService {

    private final ClothesServiceMenuRepository clothesServiceMenuRepository;
    private final AutoWashClothesRepository autoWashClothesRepository;
    private final UserRepository userRepository;

    public List<ClothesServiceMenu> getAllClothesServiceMenu() {
        return clothesServiceMenuRepository.findAll();
    }

    public void addClothesServiceMenu(ClothesServiceMenu clothesServiceMenu) {
        AutoWashClothes checkAutoWashClothes = autoWashClothesRepository.findAutoWashClothesById(clothesServiceMenu.getAutoWashClothesId());
        if(checkAutoWashClothes == null){throw new ApiException("AutoWash Car Not Found");}
        clothesServiceMenuRepository.save(clothesServiceMenu);
    }

    public void updateClothesServiceMenu(Integer id,ClothesServiceMenu clothesServiceMenu) {
        ClothesServiceMenu oldClothesServiceMenu = clothesServiceMenuRepository.findClothesServiceMenuById(id);

        if (oldClothesServiceMenu != null) {
            oldClothesServiceMenu.setServiceName(clothesServiceMenu.getServiceName());
            oldClothesServiceMenu.setPrice(clothesServiceMenu.getPrice());
            oldClothesServiceMenu.setDescription(clothesServiceMenu.getDescription());
            oldClothesServiceMenu.setServiceDurationMinutes(clothesServiceMenu.getServiceDurationMinutes());
            clothesServiceMenuRepository.save(oldClothesServiceMenu);
        }else throw new ApiException("Clothes service not updated, clothes service menu not found");
    }

    public void deleteClothesServiceMenu(Integer id) {
        ClothesServiceMenu oldClothesServiceMenu = clothesServiceMenuRepository.findClothesServiceMenuById(id);

        if (oldClothesServiceMenu != null) {
            clothesServiceMenuRepository.delete(oldClothesServiceMenu);
        }else throw new ApiException("Clothes service not deleted, clothes service menu not found");
    }

    //ex3
    public void addDiscount(Integer serviceId, Integer discount) {

        ClothesServiceMenu checkServicesMenu = clothesServiceMenuRepository.findClothesServiceMenuById(serviceId);
        if (checkServicesMenu == null) {throw new ApiException("service not found");}

        AutoWashClothes checkAutoWashCar = autoWashClothesRepository.findAutoWashClothesById(checkServicesMenu.getAutoWashClothesId());
        if (checkAutoWashCar == null) {throw new ApiException("branch not found");}


        double discountAmount = (checkServicesMenu.getPrice() * discount) / 100;
        checkServicesMenu.setPrice(checkServicesMenu.getPrice() - discountAmount);
        clothesServiceMenuRepository.save(checkServicesMenu);

    }

    //ex4
    public List<ClothesServiceMenu> getServiceByAutoWashClothes(Integer autoWashClothesId){
        return clothesServiceMenuRepository.getClothesServiceMenuByAutoWashClothesId(autoWashClothesId);
    }

    //ex14
    public ClothesServiceMenu getCheapestPrice(Integer userId) {
        User checkUser = userRepository.findUserById(userId);
        if(checkUser == null){throw new ApiException("user not found");}
        Double min = 9999999.00;
        ClothesServiceMenu tempClothesServiceMenu = null;
        for (ClothesServiceMenu c : clothesServiceMenuRepository.findAll()) {
            if (c.getPrice() < min){
                min = c.getPrice();
                tempClothesServiceMenu = c;
            }
        }
        return tempClothesServiceMenu;
    }
}
