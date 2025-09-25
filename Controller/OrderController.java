package com.charan.e_commerse_web.Controller;

import com.charan.e_commerse_web.DTO.AddToCartRequest;
import com.charan.e_commerse_web.model.OrderItem;
import com.charan.e_commerse_web.model.Users;
import com.charan.e_commerse_web.service.OrderService;
import com.charan.e_commerse_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @Autowired
    private UserService userserv;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/add")
    public ResponseEntity<OrderItem> add(@RequestBody AddToCartRequest request,
                                         Authentication authentication) {
        String userName = authentication.getName(); // comes from JWT `sub` = userName
        OrderItem saved = orderService.addToCart(request, userName);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable int orderId,
            @RequestBody Map<String, Integer> payload) {

        int change = payload.get("change");
        System.out.println("Updating orderId = " + orderId + " with change = " + change);
        OrderItem updated = orderService.updateQuantity(orderId, change);
        if (updated == null) {
            return ResponseEntity.ok("DELETED"); // frontend knows item was removed
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("delete/{userName}/{orderId}")
    public void deleteItem(@PathVariable int orderId, @PathVariable String userName){

        orderService.deleteByOrderIdAndUserName(orderId,userName);
        System.out.println("Deletion succesfull");

    }

    // GET all products selected by a user
    @GetMapping("/{userName}")
    public List<OrderItem> getOrders(@PathVariable String userName) {
        Users user = userserv.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int userId=user.getUserId();
        return orderService.findByUserName(userName);
    }

    @GetMapping("/items/{userName}")
    public int count(@PathVariable String userName) {
        int count = orderService.countByUserName(userName);
        System.out.println("Username: " + userName + ", Count: " + count);
        return count;
    }


}
