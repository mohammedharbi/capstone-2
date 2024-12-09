package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.*;
import com.example.autowash.Repository.AutoWashCarRepository;
import com.example.autowash.Repository.CarServiceMenuRepository;
import com.example.autowash.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServicesMenuService {

    private final CarServiceMenuRepository carServiceMenuRepository;
    public final AutoWashCarRepository autoWashCarRepository;
    private final UserRepository userRepository;

    public List<CarServiceMenu> getAllCarServiceMenu(){

        return carServiceMenuRepository.findAll();
    }

    public void addCarServiceMenu(CarServiceMenu carServiceMenu){
        AutoWashCar checkAutoWashCar = autoWashCarRepository.findAutoWashCarById(carServiceMenu.getAutoWashCarId());
        if(checkAutoWashCar == null){throw new ApiException("AutoWash Car Not Found");}
        carServiceMenuRepository.save(carServiceMenu);
    }

    public void updateCarServiceMenu(Integer id,CarServiceMenu carServiceMenu){
        CarServiceMenu oldCarServiceMenu = carServiceMenuRepository.findCarServiceMenuById(id);

        if(oldCarServiceMenu != null){
            oldCarServiceMenu.setServiceName(carServiceMenu.getServiceName());
            oldCarServiceMenu.setDescription(carServiceMenu.getDescription());
            oldCarServiceMenu.setPrice(carServiceMenu.getPrice());
            oldCarServiceMenu.setServiceDurationMinutes(carServiceMenu.getServiceDurationMinutes());
            carServiceMenuRepository.save(oldCarServiceMenu);
        }else throw new ApiException("Car Service Menu Not Found, cannot update");
    }

    public void deleteCarServiceMenu(Integer id){
        CarServiceMenu oldcarServiceMenu = carServiceMenuRepository.findCarServiceMenuById(id);

        if(oldcarServiceMenu != null){
            carServiceMenuRepository.delete(oldcarServiceMenu);
        }else throw new ApiException("Car Service Menu Not Found, cannot delete");
    }

    //ex3
    public void addDiscount(Integer serviceId, Integer discount) {

        CarServiceMenu checkServicesMenu = carServiceMenuRepository.findCarServiceMenuById(serviceId);
        if (checkServicesMenu == null) {throw new ApiException("service not found");}

        AutoWashCar checkAutoWashCar = autoWashCarRepository.findAutoWashCarById(checkServicesMenu.getAutoWashCarId());
        if (checkAutoWashCar == null) {throw new ApiException("branch not found");}


            double discountAmount = (checkServicesMenu.getPrice() * discount) / 100;
            checkServicesMenu.setPrice(checkServicesMenu.getPrice() - discountAmount);
            carServiceMenuRepository.save(checkServicesMenu);

    }

    //ex4
    public List<CarServiceMenu> getServiceByAutoWashCar(Integer autoWashCarId){
        return carServiceMenuRepository.getCarServiceMenuByAutoWashCarId(autoWashCarId);
    }

    //ex14
    public CarServiceMenu getCheapestPrice(Integer userId) {
        User checkUser = userRepository.findUserById(userId);
        if(checkUser == null){throw new ApiException("user not found");}
        Double min = 9999999.00;
        CarServiceMenu tempCarServiceMenu = null;
        for (CarServiceMenu c : carServiceMenuRepository.findAll()) {
            if (c.getPrice() < min){
                min = c.getPrice();
                tempCarServiceMenu = c;
            }
        }
        return tempCarServiceMenu;
    }


}
