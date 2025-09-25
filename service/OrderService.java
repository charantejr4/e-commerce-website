package com.charan.e_commerse_web.service;

import com.charan.e_commerse_web.DTO.AddToCartRequest;
import com.charan.e_commerse_web.DTO.OrderItemDTO;
import com.charan.e_commerse_web.model.OrderItem;
import com.charan.e_commerse_web.model.Product;
import com.charan.e_commerse_web.model.Users;
import com.charan.e_commerse_web.repository.OrderItemRepository;
import com.charan.e_commerse_web.repository.ProductRepository;
import com.charan.e_commerse_web.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    private final OrderItemRepository orderItemRepository;


    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderItemRepository orderItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderItem addToCart(AddToCartRequest req, String userNameFromJwt) {

        Users user = userRepository.findByUserName(userNameFromJwt)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Optional<OrderItem> existingItem = orderItemRepository.findByUsersAndProduct(user, product);

        OrderItem item;
        if (existingItem.isPresent()) {
            // ✅ update quantity
            item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
        } else {
            // ✅ create new item
            item = OrderItem.builder()
                    .users(user)
                    .product(product)
                    .userName(user.getUserName())
                    .quantity(1)   // make sure you initialize
                    .build();
        }

        return orderItemRepository.save(item);
    }

    public List<OrderItemDTO> getOrdersByUserId(int userId) {
        List<OrderItem> orders = orderItemRepository.findByUsers_UserId(userId);
        System.out.println("Fetched orders count: " + orders.size());
        return orders.stream().map(OrderItemDTO::new).collect(Collectors.toList());
    }

    public OrderItem updateQuantity(int orderId, int change) {
        OrderItem item = orderItemRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        int newQty = item.getQuantity() + change;
        if (newQty < 1) {
            // (or delete item if you want cart behavior)
            return null;
        }

        item.setQuantity(newQty);
        return orderItemRepository.save(item);
    }


    @Transactional
    public void deleteByOrderIdAndUserName(int orderId, String userName) {
        orderItemRepository.deleteByOrderIdAndUserName(orderId, userName);

    }


    public List<OrderItem> findByUserName(String userName) {
        return orderItemRepository.findByUserName(userName);
    }




    public int countByUserName(String userName) {
        return orderItemRepository.countByUserName(userName);
    }
}
