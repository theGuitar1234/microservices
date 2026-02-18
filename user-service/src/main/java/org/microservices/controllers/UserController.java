package org.microservices.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.microservices.dtos.UserDTO;
import org.microservices.exceptions.InsufficientBalanceException;
import org.microservices.exceptions.InvalidBalanceException;
import org.microservices.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(
        @RequestParam(name = "firstName") String firstname
    ) {
        UserDTO userDTO = userService.createNewUser(firstname);
        return ResponseEntity.ok(userDTO);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") long userId) {
        UserDTO userDTO;
        try {
            userDTO = userService.getUserById(userId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/{userId}/deposit")
    public ResponseEntity<?> deposit(
        @PathVariable("userId") long userId,
        @RequestParam("amount") BigDecimal amount
    ) {
        try {
            userService.deposit(userId, amount);
        } catch (InvalidBalanceException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
    

    @PostMapping("/{userId}/reserve-credit")
    public ResponseEntity<?> reserveCredit(
        @PathVariable("userId") long userId,
        @RequestParam("amount") BigDecimal amount
    ) {
        try {
            userService.reserveCredit(userId, amount);
        } catch (InvalidBalanceException | InsufficientBalanceException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
    
}
