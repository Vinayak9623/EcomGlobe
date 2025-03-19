package com.ecom.service;

import com.ecom.dto.OrderDto;

import java.util.List;

public interface OrderService {

    public OrderDto createOrder(OrderDto orderDto);
    public List<OrderDto> getAllOrders();
    public OrderDto updateOrder(OrderDto orderDto, Long id);
    public String deleteOrder(Long id);

}
