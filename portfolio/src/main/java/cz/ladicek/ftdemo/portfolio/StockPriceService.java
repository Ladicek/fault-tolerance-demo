package cz.ladicek.ftdemo.portfolio;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class StockPriceService {
    @Inject
    @RestClient
    StockPriceClient client;

    @Inject
    StatsResource stats;

    private final ConcurrentMap<String, StockPrice> cache = new ConcurrentHashMap<>();

    public StockPrice getPrice(String ticker) {
        StockPrice result = client.get(ticker);
        cache.put(ticker, result);
        stats.recordNormal();
        stats.setCacheSize(cache.size());
        return result;
    }

    private StockPrice getPriceFallback(String ticker) {
        stats.recordCached();
        return cache.getOrDefault(ticker, new StockPrice(ticker, null));
    }
}
