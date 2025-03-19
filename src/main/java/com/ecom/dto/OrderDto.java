package com.ecom.dto;

import com.ecom.entity.Product;
import com.ecom.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    private User user;
    private Product product;
    private int quantity;
    private LocalDateTime orderDate = LocalDateTime.now();
}
