package org.microservices.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
// import java.util.function.Supplier;

import org.microservices.dtos.OrderDTO;
import org.microservices.dtos.UserDTO;
import org.microservices.entities.Order;
import org.microservices.mappers.OrderMapper;
import org.microservices.metrics.OrderMetrics;
import org.microservices.repositories.OrderRepository;
import org.microservices.utilities.constants.orderStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMetrics orderMetrics;

    public void saveOrder(Order order) {
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(LocalDateTime.now());
        }
        orderRepository.save(order);
        orderRepository.flush();
    }

    public void createNewOrder(String product, BigDecimal amount) {
        Order order = new Order();
        order.setProduct(product);
        order.setAmount(amount);
        order.setStatus(orderStatus.PENDING);
        saveOrder(order);

        orderMetrics.incrementOrdersCounter();
    }

    public OrderDTO getOrderById(long orderId, UserDTO userDTO) {

        return orderMetrics.recordOrderLookup(() -> {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            Order order = orderOptional.orElseThrow(() -> new RuntimeException("Order Not Found By ID"));

            return OrderMapper.toOrderDTO(order, userDTO);
        });
    }

    public void updateStatus(long orderId, orderStatus status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        Order order = orderOptional.orElseThrow(() -> new RuntimeException("Order Not Found By ID"));
        order.setStatus(status);
        saveOrder(order);
    }
}
