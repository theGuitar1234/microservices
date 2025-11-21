package org.microservices.payloads;

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
public class CreditReservationFailedEvent {
    private long orderId;
    private long userId;
    private BigDecimal amount;
}
