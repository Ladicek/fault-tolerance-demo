package cz.ladicek.ftdemo.stock.price;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"ticker", "price"})
public class StockPrice {
    public String ticker;
    public int price;

    public StockPrice(String ticker, int price) {
        this.ticker = ticker;
        this.price = price;
    }
}
