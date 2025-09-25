package com.charan.e_commerse_web.repository;

import com.charan.e_commerse_web.model.OrderItem;
import com.charan.e_commerse_web.model.Product;
import com.charan.e_commerse_web.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
     List<OrderItem> findByUsers_UserId(int userId);
     List<OrderItem> findByUserName(String userName);
     void deleteByOrderIdAndUserName(int orderId,String userName);

     Optional<OrderItem> findByUsersAndProduct(Users user, Product product);



     int countByUserName(String userName);
}

