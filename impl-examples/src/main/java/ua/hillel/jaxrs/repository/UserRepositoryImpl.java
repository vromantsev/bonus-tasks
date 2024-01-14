package ua.hillel.jaxrs.repository;

import ua.hillel.jaxrs.dto.UserRequest;
import ua.hillel.jaxrs.model.User;
import ua.hillel.jaxrs.utils.IdGeneratorUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class UserRepositoryImpl implements UserRepository {

    private static final Map<UUID, User> ID_TO_USER_MAP = new HashMap<>();

    @Override
    public User save(UserRequest request) {
        UUID id = IdGeneratorUtils.generateId();
        User user = new User(id, request.username(), request.email());
        ID_TO_USER_MAP.put(id, user);
        return user;
    }

    @Override
    public User getById(UUID id) {
        return ID_TO_USER_MAP.get(id);
    }

    @Override
    public User update(UserRequest request) {
        return ID_TO_USER_MAP.computeIfPresent(request.id(), (id, user) -> doUpdate(request));
    }

    private static User doUpdate(UserRequest request) {
        Function<UserRequest, User> remappingFunction = (req) -> new User(request.id(), req.username(), req.email());
        return remappingFunction.apply(request);
    }

    @Override
    public boolean deleteById(UUID id) {
        return ID_TO_USER_MAP.remove(id) != null;
    }

    @Override
    public Collection<User> getAll() {
        return ID_TO_USER_MAP.values();
    }
}
