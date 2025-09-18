package com.solvians.showcase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class CertificateUpdateCallableConcurrentTest {

    private static ExecutorService executor;
    private static List<Future<CertificateUpdate>> futures;
    private static final int NUM_TASKS = 50;

    @BeforeAll
    static void setup() {
        executor = Executors.newFixedThreadPool(10);
        futures = new ArrayList<>();

        // Submit multiple callables
        for (int i = 0; i < NUM_TASKS; i++) {
            futures.add(executor.submit(new CertificateUpdateCallable()));
        }
    }

    @Test
    void testAllObjectsNotNull() throws ExecutionException, InterruptedException {
        for (Future<CertificateUpdate> future : futures) {
            CertificateUpdate update = future.get();
            assertNotNull(update, "CertificateUpdate should not be null");
            assertNotNull(update.getIsin(), "ISIN should not be null");
        }
    }

    @Test
    void testBidPriceAndAskPriceRange() throws ExecutionException, InterruptedException {
        for (Future<CertificateUpdate> future : futures) {
            CertificateUpdate update = future.get();
            BigDecimal bidPrice = update.getBidPrice();
            BigDecimal askPrice = update.getAskPrice();

            assertTrue(bidPrice.compareTo(BigDecimal.valueOf(100.0)) >= 0 &&
                            bidPrice.compareTo(BigDecimal.valueOf(200.0)) <= 0,
                    "Bid price out of range");

            assertTrue(askPrice.compareTo(BigDecimal.valueOf(100.0)) >= 0 &&
                            askPrice.compareTo(BigDecimal.valueOf(200.0)) <= 0,
                    "Ask price out of range");
        }
    }

    @Test
    void testBidAndAskSizeRange() throws ExecutionException, InterruptedException {
        for (Future<CertificateUpdate> future : futures) {
            CertificateUpdate update = future.get();
            int bidSize = update.getBidSize();
            int askSize = update.getAskSize();
            assertTrue(bidSize >= 1000 && bidSize <= 5000, "Bid size out of range");
            assertTrue(askSize >= 1000 && askSize <= 10000, "Ask size out of range");
        }
    }

    @Test
    void testBidAndAskPriceDecimalPlaces() throws ExecutionException, InterruptedException {
        for (Future<CertificateUpdate> future : futures) {
            CertificateUpdate update = future.get();

            assertEquals(2, update.getBidPrice().scale(), "Bid price should have 2 decimal places");
            assertEquals(2, update.getAskPrice().scale(), "Ask price should have 2 decimal places");
        }
    }

    @Test
    void testAllTimestampsReasonable() throws ExecutionException, InterruptedException {
        long now = System.currentTimeMillis();
        for (Future<CertificateUpdate> future : futures) {
            CertificateUpdate update = future.get();
            assertTrue(update.getTimestamp() <= now && update.getTimestamp() >= now - 10000,
                    "Timestamp should be within last 10 seconds");
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "Executor did not terminate in time");
    }
}
