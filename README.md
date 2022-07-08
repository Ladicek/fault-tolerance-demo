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

## Dashboard

This is derived from the OpenLiberty MP Fault Tolerance Grafana dashboard: https://grafana.com/grafana/dashboards/14193

```bash
# run Prometheus
docker run -it --rm --network=host -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus

# run Grafana
docker run -it --rm --network=host grafana/grafana-oss

# add a Prometheus data source to Grafana
curl -X POST -H "Content-Type: application/json" -d @grafana-data-source.json http://admin:admin@localhost:3000/api/datasources

# add a fault tolerance dashboard to Grafana
curl -X POST -H "Content-Type: application/json" -d @grafana-dashboard.json http://admin:admin@localhost:3000/api/dashboards/db
```

Prometheus is available at http://localhost:9090/.

Grafana is available at http://localhost:3000.
Log in as `admin` with password `admin`, a password change dialog will follow.
