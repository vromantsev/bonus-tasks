package ua.hillel.jaxrs.repository;

import ua.hillel.jaxrs.dto.UserRequest;
import ua.hillel.jaxrs.model.User;

import java.util.Collection;
import java.util.UUID;

public interface UserRepository {

    User save(UserRequest request);

    User getById(UUID id);

    User update(UserRequest request);

    boolean deleteById(UUID id);

    Collection<User> getAll();

}
