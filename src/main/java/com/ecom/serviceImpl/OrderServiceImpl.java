package com.ecom.serviceImpl;
import com.ecom.Repository.OrederRepository;
import com.ecom.Repository.ProductRepository;
import com.ecom.Repository.UserRepository;
import com.ecom.customException.OrderNotFoundException;
import com.ecom.customException.ProductNotFoundException;
import com.ecom.customException.UserNotFoundException;
import com.ecom.dto.OrderDto;
import com.ecom.entity.Order;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ModelMapper orderMapper;

    @Autowired
    private OrederRepository orederRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Product product = productRepository.findById(orderDto.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Order order = orderMapper.map(orderDto, Order.class);
        order.setUser(user);
        order.setProduct(product);

        orederRepository.save(order);

        return orderMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orederRepository.findAll();
        return orders.stream()
                .map(order -> orderMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long id) {

        Order existingOrder = orederRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        existingOrder.setQuantity(orderDto.getQuantity());
        existingOrder.setProduct(orderDto.getProduct());
        existingOrder.setUser(orderDto.getUser());
        existingOrder.setOrderDate(orderDto.getOrderDate());

        orederRepository.save(existingOrder);

        return orderMapper.map(existingOrder, OrderDto.class);
    }

    @Override
    public String deleteOrder(Long id) {
        Order order = orederRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orederRepository.delete(order);
        return "Order with ID " + id + " deleted successfully";
    }


}
