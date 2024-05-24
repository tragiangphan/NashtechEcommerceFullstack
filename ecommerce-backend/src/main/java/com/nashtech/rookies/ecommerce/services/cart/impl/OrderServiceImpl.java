package com.nashtech.rookies.ecommerce.services.cart.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.nashtech.rookies.ecommerce.repositories.cart.CartItemRepository;
import com.nashtech.rookies.ecommerce.repositories.user.UserRepository;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.cart.requests.OrderRequestDTO;
import com.nashtech.rookies.ecommerce.dto.cart.responses.OrderResponseDTO;
import com.nashtech.rookies.ecommerce.exceptions.ResourceNotFoundException;
import com.nashtech.rookies.ecommerce.mappers.cart.OrderMapper;
import com.nashtech.rookies.ecommerce.models.cart.Order;
import com.nashtech.rookies.ecommerce.repositories.cart.OrderRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.cart.OrderService;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl extends CommonServiceImpl<Order, Long> implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartItemRepository cartItemRepository;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, OrderMapper orderMapper,
                            CartItemRepository cartItemRepository) {
        super(orderRepository);
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        if (userRepository.existsById(orderRequestDTO.userId())) {
            Order order = new Order();
            order.setUser(userRepository.findById(orderRequestDTO.userId()).get());
            Set<Long> cartItems = order.getUser().getCart().getCartItems()
                    .stream()
                    .map(Persistable::getId)
                    .collect(Collectors.toSet());
            if (cartItems.contains(orderRequestDTO.cartItems())) {
                order.setCartItems(orderRequestDTO.cartItems().stream()
                        .map(cartItem -> cartItemRepository.findById(cartItem).get())
                        .collect(Collectors.toSet()));
                order = orderRepository.saveAndFlush(order);
                return new OrderResponseDTO(order.getId(), order.getCreatedOn(), order.getLastUpdatedOn(),
                        order.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()),
                        order.getUser().getId());
            } else {
                Set<Long> orderItems = new HashSet<>(orderRequestDTO.cartItems());
                orderItems.removeAll(cartItems);
                throw new ResourceNotFoundException("Not found these Cart Item id in Cart: " +
                        orderItems);
            }
        } else {
            throw new ResourceNotFoundException("Not found user with an id: " + orderRequestDTO.userId());
        }
    }

    @Override
    public List<OrderResponseDTO> getOrder() {
        var orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        orders.forEach(order -> {
            orderResponseDTOs.add(new OrderResponseDTO(
                    order.getId(), order.getCreatedOn(), order.getLastUpdatedOn(),
                    order.getCartItems() != null ?
                            order.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>(),
                    order.getUser().getId()));
        });
        return orderResponseDTOs;
    }

    @Override
    public List<OrderResponseDTO> getOrder(Long id) {
        if (orderRepository.existsById(id)) {
            Order order = orderRepository.findById(id).get();
            List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
            orderResponseDTOs.add(new OrderResponseDTO(
                    order.getId(), order.getCreatedOn(), order.getLastUpdatedOn(),
                    order.getCartItems() != null ?
                            order.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>(),
                    order.getUser().getId()));
            return orderResponseDTOs;
        } else {
            throw new ResourceNotFoundException("Not found a Order with an id: " + id);
        }
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        if (orderRepository.existsById(id)) {
            Order order = orderRepository.findById(id).get();
            order.setUser(userRepository.findById(orderRequestDTO.userId()).get());
            order = orderRepository.saveAndFlush(order);
            return new OrderResponseDTO(order.getId(), order.getCreatedOn(), order.getLastUpdatedOn(),
                    order.getCartItems() != null ?
                            order.getCartItems().stream().map(Persistable::getId).collect(Collectors.toSet()) : new HashSet<>(),
                    order.getUser().getId());
        } else {
            throw new ResourceNotFoundException("Not found Order with an id: " + id);
        }
    }
}
