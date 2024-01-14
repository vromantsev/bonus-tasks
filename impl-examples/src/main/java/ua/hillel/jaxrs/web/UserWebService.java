package ua.hillel.jaxrs.web;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ua.hillel.jaxrs.dto.UserDeleteResponse;
import ua.hillel.jaxrs.dto.UserRequest;
import ua.hillel.jaxrs.model.User;
import ua.hillel.jaxrs.service.UserService;

import java.util.Collection;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserWebService {

    @Inject
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(final UserRequest request) {
        User user = userService.create(request);
        return Response
                .ok()
                .entity(user)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") final UUID id) {
        User user = userService.getById(id);
        return Response
                .ok()
                .entity(user)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(final UserRequest request) {
        User user = userService.update(request);
        return Response
                .ok()
                .entity(user)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUserById(@PathParam("id") final UUID id) {
        boolean isUserDeleted = userService.deleteById(id);
        return Response
                .ok()
                .entity(UserDeleteResponse.of(id, isUserDeleted))
                .build();
    }

    @GET
    public Response getAllUsers() {
        Collection<User> users = userService.getAll();
        return Response
                .ok()
                .entity(users)
                .build();
    }
}
