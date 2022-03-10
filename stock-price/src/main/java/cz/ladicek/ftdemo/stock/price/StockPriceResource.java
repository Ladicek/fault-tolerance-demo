package cz.ladicek.ftdemo.stock.price;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/{ticker}")
public class StockPriceResource {
    private static final int FAST = 10;
    private static final int SLOW = 20;

    private final ConcurrentMap<String, Integer> prices = new ConcurrentHashMap<>();

    @Inject
    StatsResource stats;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StockPrice get(@PathParam("ticker") String ticker) {
        return stats.request(currentInFlightRequests -> {
            if (currentInFlightRequests <= FAST) {
                stats.recordOk();
                randomSleep(5, 15);
                return priceOf(ticker);
            } else if (currentInFlightRequests <= SLOW) {
                stats.recordOk();
                randomSleep(100, 1000);
                return priceOf(ticker);
            } else {
                int randomOutcome = ThreadLocalRandom.current().nextInt(10);
                if (randomOutcome < 3) {
                    stats.recordOk();
                    randomSleep(500, 5000);
                    return priceOf(ticker);
                } else if (randomOutcome < 6) {
                    stats.recordKo();
                    randomSleep(0, 5000);
                    throw new InternalServerErrorException();
                } else if (randomOutcome < 7) {
                    stats.recordKo();
                    randomSleep(0, 5000);
                    throw new BadRequestException();
                } else {
                    stats.recordKo();
                    randomSleep(0, 5000);
                    throw new WebApplicationException(Response.ok("invalid JSON", MediaType.APPLICATION_JSON_TYPE).build());
                }
            }
        });
    }

    private void randomSleep(long low, long high) {
        long sleepInMillis = ThreadLocalRandom.current().nextLong(low, high + 1);
        try {
            Thread.sleep(sleepInMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private StockPrice priceOf(String ticker) {
        int price = prices.merge(ticker, ThreadLocalRandom.current().nextInt(1000), (currentPrice, ignored) ->
                nonnegative(currentPrice + ThreadLocalRandom.current().nextInt(-10, 11)));
        return new StockPrice(ticker, price);
    }

    private static int nonnegative(int n) {
        return Math.max(0, n);
    }
}
