package org.microservices.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.microservices.dtos.OrderDTO;
import org.microservices.dtos.UserDTO;

import org.microservices.feign.UserServiceClient;
import org.microservices.services.OrderService;
import org.microservices.utilities.constants.orderStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserServiceClient userServiceClient;

    @GetMapping("/createOrder")
    public ResponseEntity<?> createOrder(
        @RequestParam("product") String product,
        @RequestParam("amount") BigDecimal amount
    ) {
        orderService.createNewOrder(product, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") long orderId) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.getOrderById(orderId, null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }
    
    @PostMapping("/{userId}/{orderId}")
    //@CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    public ResponseEntity<?> placeOrder(
        @PathVariable("userId") long userId,
        @PathVariable("orderId") long orderId
    ) {

        UserDTO userDTO;
        OrderDTO orderDTO;

        try {
            userDTO = userServiceClient.getUserById(userId);
            orderDTO = orderService.getOrderById(orderId, userDTO);
            orderService.updateStatus(orderId, orderStatus.CONFIRMED);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        try {
            userServiceClient.reserveCredit(userId, orderDTO.getAmount());
        } catch (Exception e) {
            e.printStackTrace();
            orderService.updateStatus(orderId, orderStatus.REJECTED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

    // public ResponseEntity<?> fallbackUser(long userId, long orderId, Throwable throwable) {
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserDTO(userId, "Unknown fallback user for User ID: " + userId + " not found"));
    // }
    
}

