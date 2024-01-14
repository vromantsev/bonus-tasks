package ua.hillel.jaxrs.config;

import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import ua.hillel.jaxrs.repository.UserRepository;
import ua.hillel.jaxrs.repository.UserRepositoryImpl;
import ua.hillel.jaxrs.service.UserService;
import ua.hillel.jaxrs.service.UserServiceImpl;

public class ApplicationBinder implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(UserRepositoryImpl.class).to(UserRepository.class);
                bind(UserServiceImpl.class).to(UserService.class);
                bind(JacksonJsonProvider.class);
            }
        });
        return true;
    }
}
