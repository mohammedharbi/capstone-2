package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.*;
import com.example.autowash.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAutoWashCarService {

    private final OrderAutoWashCarRepository orderAutoWashCarRepository;
    private final CarServiceMenuRepository carServiceMenuRepository;
    private final AutoWashCarRepository autoWashCarRepository;
    private final UserRepository userRepository;
    private final WashingMachineCarRepository washingMachineCarRepository;
    private final WashingMachineCarService washingMachineCarService;

    public List<OrderAutoWashCar> getOrderAutoWashCar() {
        return orderAutoWashCarRepository.findAll();
    }

    public WashingMachineCar addOrderAutoWashCar(OrderAutoWashCar orderAutoWashCar) {
        User checkUser = userRepository.findUserById(orderAutoWashCar.getUserId());
        if (checkUser == null) {
            throw new ApiException("User Not Found");
        }

        AutoWashCar checkautoWashCar = autoWashCarRepository.findAutoWashCarById(orderAutoWashCar.getAutoWashCarId());
        if (checkautoWashCar == null) {
            throw new ApiException("Auto wash car Not Found");
        }
        if (checkautoWashCar.getStatus().equals("Closed")) {
            throw new ApiException("Auto wash car Closed");
        }

        CarServiceMenu checkCarServiceMenu = carServiceMenuRepository.findCarServiceMenuById(orderAutoWashCar.getCarServiceMenuId());
        if (checkCarServiceMenu == null) {
            throw new ApiException("Service Not Found");
        }

        if (checkUser.getBalance() < checkCarServiceMenu.getPrice()) {
            throw new ApiException("Not Enough Balance");
        }

        for (WashingMachineCar w : washingMachineCarRepository.findAll()) {
            if (orderAutoWashCar.getAutoWashCarId().equals(w.getAutoWashCarId())) {
                if (w.getWashingMachineStatus().equals("Available")) {

                    w.setWashingMachineStatus("Busy"); // change status of washing machine to 'Busy'
                    w.setPin(washingMachineCarService.generatePin()); //generate a new pin
                    w.setCreatedAt(LocalDateTime.now()); // to compare it  with serviceDuration when adding new order and changing order status to 'Completed' if the serviceDuration finished
                    washingMachineCarRepository.save(w);

                    checkUser.setBalance(checkUser.getBalance() - checkCarServiceMenu.getPrice()); // deduct balance from user
                    checkUser.setUserOrders(checkUser.getUserOrders() + 1); // increasing the number of orders for the user
                    checkautoWashCar.setOrderCapacity(checkautoWashCar.getOrderCapacity() - 1);// decreasing orderCapacity from branch if it reach 0 means it 'Busy'
                    OrderAutoWashCar newOrderAutoWashCar = new OrderAutoWashCar();
                    newOrderAutoWashCar.setAutoWashCarId(orderAutoWashCar.getAutoWashCarId());
                    newOrderAutoWashCar.setCarServiceMenuId(orderAutoWashCar.getCarServiceMenuId());
                    newOrderAutoWashCar.setWashingMachineCarId(w.getId());
                    newOrderAutoWashCar.setCreatedAt(LocalDateTime.now());
                    newOrderAutoWashCar.setTotalCost(checkCarServiceMenu.getPrice());
                    newOrderAutoWashCar.setUserId(orderAutoWashCar.getUserId());
                    newOrderAutoWashCar.setStatus("Pending");
                    orderAutoWashCarRepository.save(newOrderAutoWashCar);
                    return w;
                }
            }
        }
        return null;
    }

    @Scheduled(cron = "0/10 * * ? * *") // Runs every 10 seconds
    public void updateOrderStatus() {

        for (AutoWashCar awc : autoWashCarRepository.findAll()) {
            if (!awc.getStatus().contains("Closed")) {
                if (awc.getOrderCapacity() == 0) {
                    awc.setStatus("Busy");
                }
                autoWashCarRepository.save(awc);

                if (awc.getOrderCapacity() >= 1) {
                    awc.setStatus("Open");
                    autoWashCarRepository.save(awc);
                }
            }
        }

        for (OrderAutoWashCar o : orderAutoWashCarRepository.findAll()) {
            if (o.getStatus().equals("Pending")) {
                CarServiceMenu updateCarServiceMenuStatus = carServiceMenuRepository.findCarServiceMenuById(o.getCarServiceMenuId());
                WashingMachineCar updateWashingMachineCarStatus = washingMachineCarRepository.findWashingMachineCarById(o.getWashingMachineCarId());
                AutoWashCar updateAutoWashCarCapacity = autoWashCarRepository.findAutoWashCarById(o.getAutoWashCarId());

                if (updateWashingMachineCarStatus != null && updateWashingMachineCarStatus.getWashingMachineStatus().equals("Busy") && updateWashingMachineCarStatus.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(updateCarServiceMenuStatus.getServiceDurationMinutes()))) {

                    // Update order and machine status
                    o.setStatus("Completed");
                    orderAutoWashCarRepository.save(o);

                    updateWashingMachineCarStatus.setWashingMachineStatus("Available");
                    washingMachineCarRepository.save(updateWashingMachineCarStatus);
                    updateAutoWashCarCapacity.setOrderCapacity(updateAutoWashCarCapacity.getOrderCapacity() + 1);
                    autoWashCarRepository.save(updateAutoWashCarCapacity);

                }
            }
        }
    }
    //ex7
    public List<OrderAutoWashCar> userOrderAutoWashCarHistory(Integer userId){
        User user = userRepository.findUserById(userId);
        if (user == null) {throw new ApiException("User Not Found");}

        return orderAutoWashCarRepository.getOrderAutoWashCarsByUserId(userId);
    }

    //ex9
    public void orderCancellation(Integer userid, Integer orderID){

        User checkUser = userRepository.findUserById(userid);
        if (checkUser == null) {throw new ApiException("User Not Found");}

        OrderAutoWashCar checkOrder = orderAutoWashCarRepository.findOrderAutoWashCarById(orderID);
        if (checkOrder == null) {throw new ApiException("Order Not Found");}

        if (checkOrder.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            checkOrder.setStatus("Cancelled");
            orderAutoWashCarRepository.save(checkOrder);
            checkUser.setBalance(checkUser.getBalance() + checkOrder.getTotalCost());
            checkUser.setUserOrders(checkUser.getUserOrders() + 1);
            userRepository.save(checkUser);
        }else throw new ApiException("Order Cancellation Failed, user must request cancellation for order within 1 minutes");
        }


}
