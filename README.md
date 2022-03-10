# Fault Tolerance Demo

This is a demo project for Quarkus Insights #83.

It consists of 3 modules:

- `stock-price`: demo service that provides current price for a stock ticker
- `portfolio`: demo service that maintains stock portfolios for users, uses `stock-price` to obtain current price
- `user`: simulation of users that use `portfolio`

## Real World Comparison

I don't know anything about stock trading, portfolio management etc.
The architecture of this demo project has nothing to do with anything you can possibly find in the real world.
That's OK.
It's just a demo.

## Usage

This is _not_ a multi-module Maven project.
There's a parent POM only for centralizing some boilerplate.
All modules must be built independently.

### `stock-price`

```bash
cd stock-price
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar # http://localhost:5050/
```

```bash
java -Dquarkus.http.port=6060 -jar target/quarkus-app/quarkus-run.jar # http://localhost:6060/
```

### `portfolio`

We'll run this in dev mode as we'll be doing live changes to the `StockPriceService` class.
Specifically, we'll add annotations to the `getPrice` method.

```bash
cd portfolio
mvn clean quarkus:dev # http://localhost:7070/
```

### `user`

```bash
cd user
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar # http://localhost:8080/
```
