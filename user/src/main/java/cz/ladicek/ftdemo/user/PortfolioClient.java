package cz.ladicek.ftdemo.user;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "portfolio")
public interface PortfolioClient {
    @GET
    @Path("/{user}")
    Portfolio get(@PathParam("user") String user);
}
