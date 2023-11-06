package cz.ladicek.ftdemo.portfolio;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "stock-price")
public interface StockPriceClient {
    @GET
    @Path("/{ticker}")
    StockPrice get(@PathParam("ticker") String ticker);
}
