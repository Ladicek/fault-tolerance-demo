package cz.ladicek.ftdemo.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"size", "totalPrice", "portfolio"})
public class Portfolio {
    public int size;
    public Integer totalPrice;
    public List<StockPrice> portfolio;

    @JsonCreator
    public Portfolio(@JsonProperty("size") int size, @JsonProperty("totalPrice") Integer totalPrice, @JsonProperty("portfolio") List<StockPrice> portfolio) {
        this.size = size;
        this.totalPrice = totalPrice;
        this.portfolio = portfolio;
    }
}
