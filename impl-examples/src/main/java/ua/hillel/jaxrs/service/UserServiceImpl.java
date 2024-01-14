package ua.hillel.jaxrs.service;

import jakarta.inject.Inject;
import ua.hillel.jaxrs.dto.UserRequest;
import ua.hillel.jaxrs.model.User;
import ua.hillel.jaxrs.repository.UserRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public User create(UserRequest request) {
        Objects.requireNonNull(request, "Parameter [request] must not be null!");
        if (request.id() != null) {
            throw new IllegalArgumentException("Id must not be provided!");
        }
        return userRepository.save(request);
    }

    @Override
    public User getById(UUID id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        return userRepository.getById(id);
    }

    @Override
    public User update(UserRequest request) {
        Objects.requireNonNull(request, "Parameter [request] must not be null!");
        if (request.id() == null) {
            throw new IllegalArgumentException("Id must be provided!");
        }
        return userRepository.update(request);
    }

    @Override
    public boolean deleteById(UUID id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        return userRepository.deleteById(id);
    }

    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }
}
