package org.microservices.repositories;

import java.util.Optional;

import org.microservices.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(long orderId);
}
