package org.microservices.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.microservices.utilities.constants.orderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long orderId;

    @Column
    private String product;

    @Column
    private BigDecimal amount;

    @Column
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private orderStatus status;
}
