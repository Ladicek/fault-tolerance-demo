package cz.ladicek.ftdemo.user;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient(configKey = "portfolio")
public interface PortfolioClient {
    @GET
    @Path("/{user}")
    Portfolio get(@PathParam("user") String user);
}
