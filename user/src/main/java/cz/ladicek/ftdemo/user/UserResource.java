package cz.ladicek.ftdemo.user;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Path("/users")
public class UserResource {
    private final ConcurrentMap<String, UserSimulation> users = new ConcurrentHashMap<>();

    @Inject
    @RestClient
    PortfolioClient client;

    @GET
    public List<User> get() {
        return users.values()
                .stream()
                .map(UserSimulation::toUser)
                .collect(Collectors.toList());
    }

    @POST
    public void add() {
        String user = Names.random();
        UserSimulation simulation = new UserSimulation(user, client);
        users.put(user, simulation);
    }

    @Path("/{user}")
    @DELETE
    public void remove(@PathParam("user") String user) {
        UserSimulation simulation = users.remove(user);
        if (simulation != null) {
            simulation.destroy();
        }
    }
}
