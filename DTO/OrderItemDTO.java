package com.charan.e_commerse_web.DTO;

import com.charan.e_commerse_web.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private int orderId;
    private String productName;
    private String categoryName;
    private String photoLink;
    private int quantity;

    public OrderItemDTO(OrderItem item) {
        this.orderId = item.getOrderId();
        this.productName = item.getProduct().getProductName();
        this.categoryName = item.getProduct().getCategoryName();
        this.photoLink = item.getProduct().getPhotoLink();
        this.quantity = item.getQuantity();
    }
}
