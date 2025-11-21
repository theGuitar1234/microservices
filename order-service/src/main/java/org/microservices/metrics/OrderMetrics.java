package org.microservices.metrics;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Component
public class OrderMetrics {
    private final Counter ordersCounter;
    private final Timer orderTimer;

    public OrderMetrics(MeterRegistry meterRegistry) {
        this.ordersCounter = Counter
            .builder("orders_created_total")
            .description("Total number of orders created")
            .register(meterRegistry);
        this.orderTimer = Timer
            .builder("orders_get_latency")
            .description("Latency of getting orders")
            .register(meterRegistry);
    }

    public void incrementOrdersCounter() {
        ordersCounter.increment();
    }

    public <T> T recordOrderLookup(Supplier<T> supplier) {
        return orderTimer.record(supplier);
    }
}
