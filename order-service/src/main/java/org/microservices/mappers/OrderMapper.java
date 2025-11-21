package org.microservices.mappers;

import org.microservices.dtos.UserDTO;
import org.microservices.dtos.OrderDTO;
import org.microservices.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public static OrderDTO toOrderDTO(Order order, UserDTO userDTO) {
        return new OrderDTO(order.getOrderId(), order.getProduct(), order.getAmount(), userDTO);
    }
}
