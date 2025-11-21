package org.microservices.repositories;

import java.util.Optional;

import org.microservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long userId);
}
