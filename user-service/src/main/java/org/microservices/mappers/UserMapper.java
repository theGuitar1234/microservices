package org.microservices.mappers;

import org.microservices.dtos.UserDTO;
import org.microservices.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUserId(), user.getFirstname());
    }
}
