package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.User;
import com.example.autowash.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public void addUser(User user) {
        userRepository.save(user);
    }
    public void updateUser(Integer id,User user) {
        User oldUser = userRepository.findUserById(id);
        if(oldUser != null) {
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            userRepository.save(oldUser);
        }else throw new ApiException("cannot update user, user not found");
    }
    public void deleteUser(Integer id) {
        User oldUser = userRepository.findUserById(id);
        if(oldUser != null) {
            userRepository.delete(oldUser);
        }else throw new ApiException("cannot delete user, user not found");
    }

    //ex1
    public void addBalance(Integer id, double amount) {
        User oldUser = userRepository.findUserById(id);
        if(oldUser != null) {
            oldUser.setBalance(oldUser.getBalance() + amount);
            userRepository.save(oldUser);
        }else throw new ApiException("cannot add balance, user not found");
    }

    //ex2
    public void transferBalance(Integer fromUserid,Integer toUserid, double amount) {
        User fromUser = userRepository.findUserById(fromUserid);
        User toUser = userRepository.findUserById(toUserid);

        if (fromUser == null){throw new ApiException("from User not found");}
        if (toUser == null){throw new ApiException("to User not found");}

        if (fromUser.getBalance() >= amount){
            toUser.setBalance(toUser.getBalance() + amount);
            fromUser.setBalance(fromUser.getBalance() - amount);
            userRepository.save(toUser);
            userRepository.save(fromUser);
        }else throw new ApiException("cannot transfer from User not enough balance");
    }

    //ex8
    public void giftBalanceFiveOrders(Integer userId){
        User checkUser = userRepository.findUserById(userId);
        if (checkUser == null) {throw new ApiException("User Not Found");}

        if (checkUser.getUserOrders() >= 5){
            checkUser.setBalance(checkUser.getBalance() + 5);
            checkUser.setUserOrders(0);
            userRepository.save(checkUser);
        }else throw new ApiException("User must reach 5 orders to get 5$ balance");
    }

    //ex15
    public String searchUser(Integer searchingUserId, Integer searchedUserId) {
        User searchingUser = userRepository.findUserById(searchingUserId);
        if (searchingUser == null) {throw new ApiException("Searching user Not Found");}

        User searchedUser = userRepository.findUserById(searchedUserId);
        if (searchedUser == null) {throw new ApiException("Searched User Not Found");}

        String str = "result: Full name"+ searchingUser.getFullName()+" User name:"+searchedUser.getUsername();
        return str;
    }



}