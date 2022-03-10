package cz.ladicek.ftdemo.portfolio;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient(configKey = "stock-price")
public interface StockPriceClient {
    @GET
    @Path("/{ticker}")
    StockPrice get(@PathParam("ticker") String ticker);
}
