package com.example.autowash.Controller;

import com.example.autowash.Model.OrderAutoWashClothes;
import com.example.autowash.Service.OrderAutoWashClothesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-auto-wash-clothes")
@RequiredArgsConstructor
public class OrderAutoWashClothesController {

    private final OrderAutoWashClothesService orderAutoWashClothesService;

    @GetMapping("/get")
    public ResponseEntity getOrderAutoWashClothes() {
        return ResponseEntity.status(200).body(orderAutoWashClothesService.getOrderAutoWashClothes());
    }

    @PostMapping("/add")
    public ResponseEntity addOrderAutoWashClothes(@RequestBody @Valid OrderAutoWashClothes orderAutoWashClothes, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(201).body(orderAutoWashClothesService.addOrderAutoWashClothes(orderAutoWashClothes));
    }

    @PutMapping("/updateOrderStatus")
    public ResponseEntity updateOrderStatus() {
        orderAutoWashClothesService.updateOrderStatus();
        return ResponseEntity.status(200).body("Order auto wash Clothes status updated");
    }

    @GetMapping("/user-order-auto-wash-clothes-history/{userId}")
    public ResponseEntity getOrderAutoWashClothesHistory(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(orderAutoWashClothesService.userOrderAutoWashClothesHistory(userId));
    }

    @PutMapping("/user-order-cancellation/user/{userId}/order/{orderId}")
    public ResponseEntity userOrderCancellation(@PathVariable Integer userId, @PathVariable Integer orderId) {
        orderAutoWashClothesService.orderCancellation(userId, orderId);
        return ResponseEntity.status(200).body("auto wash Clothes Order canceled");
    }
}