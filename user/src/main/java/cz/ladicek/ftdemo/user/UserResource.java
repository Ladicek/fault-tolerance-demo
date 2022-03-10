package cz.ladicek.ftdemo.user;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
