package ua.hillel.jaxrs.service;

import ua.hillel.jaxrs.dto.UserRequest;
import ua.hillel.jaxrs.model.User;

import java.util.Collection;
import java.util.UUID;

public interface UserService {

    User create(UserRequest request);

    User getById(UUID id);

    User update(UserRequest request);

    boolean deleteById(UUID id);

    Collection<User> getAll();

}
