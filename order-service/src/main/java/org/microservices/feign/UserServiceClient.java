package org.microservices.feign;

import java.math.BigDecimal;

import org.microservices.configurations.FeignSecurityConfiguration;
import org.microservices.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "user-service",
    configuration = FeignSecurityConfiguration.class
)
public interface UserServiceClient {
    @GetMapping("/users/{userId}")
    UserDTO getUserById(@PathVariable("userId") long userId);

    @PostMapping("/users/{userId}/reserve-credit")
    ResponseEntity<?> reserveCredit(@PathVariable("userId") long userId, @RequestParam("amount") BigDecimal amount);
} 
