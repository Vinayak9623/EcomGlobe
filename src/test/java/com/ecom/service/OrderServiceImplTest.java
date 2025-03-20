package com.ecom.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ecom.customException.UserNotFoundException;
import com.ecom.dto.OrderDto;
import com.ecom.entity.Order;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.customException.ProductNotFoundException;
import com.ecom.Repository.OrederRepository;
import com.ecom.Repository.ProductRepository;
import com.ecom.Repository.UserRepository;
import com.ecom.serviceImpl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceImplTest {

    @Mock
    private OrederRepository orederRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateOrder() {
        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        OrderDto orderDto = OrderDto.builder().user(user).product(product).quantity(2).build();
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(2);

        OrderDto returnedOrderDto = OrderDto.builder().user(user).product(product).quantity(2).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderMapper.map(orderDto, Order.class)).thenReturn(order);
        when(orederRepository.save(order)).thenReturn(order);
        when(orderMapper.map(order, OrderDto.class)).thenReturn(returnedOrderDto);


        OrderDto result = orderService.createOrder(orderDto);


        assertEquals(returnedOrderDto, result);
        verify(orederRepository).save(order);
    }

    @Test
    void testCreateOrder_UserNotFound() {

        OrderDto orderDto = new OrderDto();
        orderDto.setUser(new User());
        orderDto.setProduct(new Product());

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> orderService.createOrder(orderDto));
    }

    @Test
    void testCreateOrder_ProductNotFound() {

        User user = new User();
        user.setId(1L);

        OrderDto orderDto = new OrderDto();
        orderDto.setUser(user);
        orderDto.setProduct(new Product());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(orderDto));
    }


    @Test
    void testGetOrders() {

        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        Order order = new Order(1l, user, product, 2, LocalDateTime.now());
        Order order1 = new Order(2l, user, product, 3, LocalDateTime.now());
        OrderDto orderDto = OrderDto.builder().user(user).product(product).quantity(2).build();
        OrderDto orderDto1 = OrderDto.builder().user(user).product(product).quantity(3).build();

        when(orederRepository.findAll()).thenReturn(List.of(order, order1));
        when(orderMapper.map(order, OrderDto.class)).thenReturn(orderDto);
        when(orderMapper.map(order1, OrderDto.class)).thenReturn(orderDto1);

        List<OrderDto> orderDtos = orderService.getAllOrders();


        assertEquals(2, orderDtos.size());
        assertEquals(2, orderDtos.get(0).getQuantity());
        verify(orederRepository, times(1)).findAll();
    }


    @Test
    void testUpdateOrder() {
        Long orderId = 1L;

        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        Order existingOrder = new Order(1L, user, product, 2, LocalDateTime.now());

        OrderDto updateorderDto = OrderDto.builder()
                .user(user)
                .product(product)
                .quantity(5)
                .orderDate(LocalDateTime.now())
                .build();

        when(orederRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderMapper.map(existingOrder, OrderDto.class)).thenReturn(updateorderDto);

        OrderDto result = orderService.updateOrder(updateorderDto, orderId);

        assertEquals(result, updateorderDto);

        verify(orederRepository, times(1)).save(existingOrder);

        assertEquals(5, existingOrder.getQuantity());
        assertEquals(product, existingOrder.getProduct());
        assertEquals(user, existingOrder.getUser());
        assertEquals(updateorderDto.getOrderDate(), existingOrder.getOrderDate());
    }

    @Test
    void testDeleteOrder_Success() {
        Long orderId = 1L;

        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        Order order = new Order(orderId, user, product, 2, LocalDateTime.now());
        when(orederRepository.findById(orderId)).thenReturn(Optional.of(order));

        String result = orderService.deleteOrder(orderId);

        assertEquals("Order with ID " + orderId + " deleted successfully", result);

        verify(orederRepository, times(1)).delete(order);
    }


}
