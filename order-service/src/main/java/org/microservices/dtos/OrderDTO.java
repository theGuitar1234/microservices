package org.microservices.dtos;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private long orderId;
    private String productString;
    private BigDecimal amount;
    private UserDTO userDTO;
}
