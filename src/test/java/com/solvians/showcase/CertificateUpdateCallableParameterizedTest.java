package com.solvians.showcase;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class CertificateUpdateCallableParameterizedTest {

    private static final int THREAD_POOL_SIZE = 10;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
    void testConcurrentCallableExecution(int ignored) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CertificateUpdateCallable callable = new CertificateUpdateCallable();

        Future<CertificateUpdate> future = executor.submit(callable);
        CertificateUpdate update = future.get();

        // Validate the CertificateUpdate
        assertNotNull(update, "CertificateUpdate should not be null");
        assertNotNull(update.getIsin(), "ISIN should not be null");

        BigDecimal bidPrice = update.getBidPrice();
        BigDecimal askPrice = update.getAskPrice();

        // Check price ranges
        assertTrue(bidPrice.compareTo(BigDecimal.valueOf(100.0)) >= 0 &&
                        bidPrice.compareTo(BigDecimal.valueOf(200.0)) <= 0,
                "Bid price out of range");

        assertTrue(askPrice.compareTo(BigDecimal.valueOf(100.0)) >= 0 &&
                        askPrice.compareTo(BigDecimal.valueOf(200.0)) <= 0,
                "Ask price out of range");

        // Check decimal places
        assertEquals(2, bidPrice.scale(), "Bid price should have 2 decimal places");
        assertEquals(2, askPrice.scale(), "Ask price should have 2 decimal places");

        // Check size ranges
        assertTrue(update.getBidSize() >= 1000 && update.getBidSize() <= 5000, "Bid size out of range");
        assertTrue(update.getAskSize() >= 1000 && update.getAskSize() <= 10000, "Ask size out of range");

        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "Executor did not terminate in time");
    }
}
