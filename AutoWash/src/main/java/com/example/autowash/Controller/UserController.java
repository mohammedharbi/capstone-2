package com.example.autowash.Controller;

import com.example.autowash.ApiResponse.ApiResponse;
import com.example.autowash.Model.User;
import com.example.autowash.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUsers(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        userService.updateUser(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted"));
    }

    @PutMapping("/add-balance/user/{userId}/balance/{balance}")
    public ResponseEntity addUserBalance(@PathVariable Integer userId, @PathVariable Double balance) {
        userService.addBalance(userId, balance);
        return ResponseEntity.status(200).body(new ApiResponse("User balance added"));
    }

    @PutMapping("/transferBalance/from-user/{fromUser}/to-user/{toUser}/balance/{balance}")
    public ResponseEntity transferUserBalance(@PathVariable Integer fromUser, @PathVariable Integer toUser, @PathVariable Double balance) {
        userService.transferBalance(fromUser, toUser, balance);
        return ResponseEntity.status(200).body(new ApiResponse("User balance transferred"));
    }

    @PutMapping("/gift-balance-five-orders/{userId}")
    public ResponseEntity giftBalanceFiveOrders(@PathVariable Integer userId) {
        userService.giftBalanceFiveOrders(userId);
        return ResponseEntity.status(200).body(new ApiResponse("User gifted 5$ balance for reaching five orders"));
    }

    @GetMapping("/search-user/searching-user/{searchingUserId}/searchedUser/{searchedUserId}")
    public ResponseEntity searchUser(@PathVariable Integer searchingUserId, @PathVariable Integer searchedUserId) {
        return ResponseEntity.ok(userService.searchUser(searchingUserId, searchedUserId));
    }
}