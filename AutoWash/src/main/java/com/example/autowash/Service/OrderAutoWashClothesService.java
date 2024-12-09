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
public class OrderAutoWashClothesService {

    private final OrderAutoWashClothesRepository orderAutoWashClothesRepository;
    private final ClothesServiceMenuRepository clothesServiceMenuRepository;
    private final AutoWashClothesRepository autoWashClothesRepository;
    private final UserRepository userRepository;
    private final WashingMachineClothesRepository washingMachineClothesRepository;
    private final WashingMachineClothesService washingMachineClothesService;

    public List<OrderAutoWashClothes> getOrderAutoWashClothes() {
        return orderAutoWashClothesRepository.findAll();
    }

    public WashingMachineClothes addOrderAutoWashClothes(OrderAutoWashClothes orderAutoWashClothes) {
        User checkUser = userRepository.findUserById(orderAutoWashClothes.getUserId());
        if (checkUser == null) {
            throw new ApiException("User Not Found");
        }

        AutoWashClothes checkAutoWashClothes = autoWashClothesRepository.findAutoWashClothesById(orderAutoWashClothes.getAutoWashClothesId());
        if (checkAutoWashClothes == null) {
            throw new ApiException("Auto wash car Not Found");
        }
        if (checkAutoWashClothes.getStatus().equals("Closed")) {
            throw new ApiException("Auto wash car Closed");
        }

        ClothesServiceMenu checkClothesServiceMenu = clothesServiceMenuRepository.findClothesServiceMenuById(orderAutoWashClothes.getClothesServiceMenuId());
        if (checkClothesServiceMenu == null) {
            throw new ApiException("Service Not Found");
        }

        if (checkUser.getBalance() < checkClothesServiceMenu.getPrice()) {
            throw new ApiException("Not Enough Balance");
        }

        for (WashingMachineClothes w : washingMachineClothesRepository.findAll()) {
            if (orderAutoWashClothes.getAutoWashClothesId().equals(w.getAutoWashClothesId())) {
                if (w.getWashingMachineStatus().equals("Available")) {

                    w.setWashingMachineStatus("Busy"); // change status of washing machine to 'Busy'
                    w.setPin(washingMachineClothesService.generatePin()); //generate a new pin
                    w.setCreatedAt(LocalDateTime.now()); // to compare it  with serviceDuration when adding new order and changing order status to 'Completed' if the serviceDuration finished
                    washingMachineClothesRepository.save(w);

                    checkUser.setBalance(checkUser.getBalance() - checkClothesServiceMenu.getPrice()); // deduct balance from user
                    checkUser.setUserOrders(checkUser.getUserOrders() + 1); // increasing the number of orders for the user
                    checkAutoWashClothes.setOrderCapacity(checkAutoWashClothes.getOrderCapacity() - 1);// decreasing orderCapacity from branch if it reach 0 means it 'Busy'
                    OrderAutoWashClothes newOrderAutoWashClothes = new OrderAutoWashClothes();
                    newOrderAutoWashClothes.setAutoWashClothesId(orderAutoWashClothes.getAutoWashClothesId());
                    newOrderAutoWashClothes.setClothesServiceMenuId(orderAutoWashClothes.getClothesServiceMenuId());
                    newOrderAutoWashClothes.setWashingMachineClothesId(w.getId());
                    newOrderAutoWashClothes.setCreatedAt(LocalDateTime.now());
                    newOrderAutoWashClothes.setTotalCost(checkClothesServiceMenu.getPrice());
                    newOrderAutoWashClothes.setUserId(orderAutoWashClothes.getUserId());
                    newOrderAutoWashClothes.setStatus("Pending");
                    orderAutoWashClothesRepository.save(newOrderAutoWashClothes);
                    return w;
                }
            }
        }
        return null;
    }

    @Scheduled(cron = "0/10 * * ? * *") // Runs every 10 seconds
    public void updateOrderStatus() {

        for (AutoWashClothes awc : autoWashClothesRepository.findAll()) {
            if (!awc.getStatus().contains("Closed")) {
                if (awc.getOrderCapacity() == 0) {
                    awc.setStatus("Busy");
                }
                autoWashClothesRepository.save(awc);

                if (awc.getOrderCapacity() >= 1) {
                    awc.setStatus("Open");
                    autoWashClothesRepository.save(awc);
                }
            }
        }

        for (OrderAutoWashClothes o : orderAutoWashClothesRepository.findAll()) {
            if (o.getStatus().equals("Pending")) {
                ClothesServiceMenu updateClothesServiceMenuStatus = clothesServiceMenuRepository.findClothesServiceMenuById(o.getClothesServiceMenuId());
                WashingMachineClothes updateWashingMachineClothesStatus = washingMachineClothesRepository.findWashingMachineClothesById(o.getWashingMachineClothesId());
                AutoWashClothes updateAutoWashClothesCapacity = autoWashClothesRepository.findAutoWashClothesById(o.getAutoWashClothesId());

                if (updateWashingMachineClothesStatus != null && updateWashingMachineClothesStatus.getWashingMachineStatus().equals("Busy") && updateWashingMachineClothesStatus.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(updateClothesServiceMenuStatus.getServiceDurationMinutes()))) {

                    // Update order and machine status
                    o.setStatus("Completed");
                    orderAutoWashClothesRepository.save(o);

                    updateWashingMachineClothesStatus.setWashingMachineStatus("Available");
                    washingMachineClothesRepository.save(updateWashingMachineClothesStatus);
                    updateAutoWashClothesCapacity.setOrderCapacity(updateAutoWashClothesCapacity.getOrderCapacity() + 1);
                    autoWashClothesRepository.save(updateAutoWashClothesCapacity);

                }
            }
        }
    }
    //ex7
    public List<OrderAutoWashClothes> userOrderAutoWashClothesHistory(Integer userId){
        User user = userRepository.findUserById(userId);
        if (user == null) {throw new ApiException("User Not Found");}

        return orderAutoWashClothesRepository.getOrderAutoWashClothesByUserId(userId);
    }

    //ex9
    public void orderCancellation(Integer userid, Integer orderID){

        User checkUser = userRepository.findUserById(userid);
        if (checkUser == null) {throw new ApiException("User Not Found");}

        OrderAutoWashClothes checkOrder = orderAutoWashClothesRepository.findOrderAutoWashClothesById(orderID);
        if (checkOrder == null) {throw new ApiException("Order Not Found");}

        if (checkOrder.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            checkOrder.setStatus("Cancelled");
            orderAutoWashClothesRepository.save(checkOrder);
            checkUser.setBalance(checkUser.getBalance() + checkOrder.getTotalCost());
            checkUser.setUserOrders(checkUser.getUserOrders() + 1);
            userRepository.save(checkUser);
        }else throw new ApiException("Order Cancellation Failed, user must request cancellation for order within 1 minutes");
    }



}
