package org.microservices.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.microservices.dtos.UserDTO;
import org.microservices.entities.User;
import org.microservices.exceptions.InsufficientBalanceException;
import org.microservices.exceptions.InvalidBalanceException;
import org.microservices.mappers.UserMapper;
import org.microservices.repositories.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
        userRepository.flush();
    }

    public UserDTO getUserById(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User Not Found By ID"));
        return UserMapper.toUserDTO(user);
    }

    public void createNewUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstname(userDTO.getFirstNameString());
        user.setCreditBalance(new BigDecimal(0));
        saveUser(user);
    }

    public void deposit(long userId, BigDecimal amount) throws InvalidBalanceException {
        if (amount == null || amount.compareTo(new BigDecimal(0)) == -1) {
            throw new InvalidBalanceException();
        }
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User Not Found By ID"));
        user.setCreditBalance(user.getCreditBalance().add(amount));
        saveUser(user);
    }

    public void reserveCredit(long userId, BigDecimal amount) 
            throws InvalidBalanceException, InsufficientBalanceException {
        if (amount == null || amount.compareTo(new BigDecimal(0)) == -1) {
            throw new InvalidBalanceException();
        }
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User Not Found By ID"));
        if (user.getCreditBalance().compareTo(amount) == -1) {
            throw new InsufficientBalanceException();
        }
        user.setCreditBalance(user.getCreditBalance().subtract(amount));
        saveUser(user);
    }
}
