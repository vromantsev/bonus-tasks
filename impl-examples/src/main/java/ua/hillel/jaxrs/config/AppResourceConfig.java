package ua.hillel.jaxrs.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import ua.hillel.jaxrs.web.UserWebService;

@ApplicationPath("/api")
public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
        register(UserWebService.class);
        register(ApplicationBinder.class);
    }
}
