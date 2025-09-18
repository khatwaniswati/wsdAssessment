package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateUpdateGeneratorTest {

    @Test
    void testGenerateQuotesCount() {
        int numQuotes = 1000;
        int threads = 10;

        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(threads, numQuotes);
        Stream<CertificateUpdate> updates = generator.generateQuotes();

        assertEquals(numQuotes, updates.count(), "Should generate exact number of quotes");
    }

    @Test
    void testGenerateQuotesNotNull() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(5, 50);
        Stream<CertificateUpdate> updates = generator.generateQuotes();

        updates.forEach(update -> {
            assertNotNull(update, "CertificateUpdate should not be null");
            assertNotNull(update.getIsin(), "ISIN should not be null");
            assertNotNull(update.getBidPrice(), "Bid price should not be null");
            assertNotNull(update.getAskPrice(), "Ask price should not be null");
        });
    }

    @Test
    void testBidAndAskPriceRangeAndDecimals() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(5, 100);
        Stream<CertificateUpdate> updates = generator.generateQuotes();

        updates.forEach(update -> {
            BigDecimal bid = update.getBidPrice();
            BigDecimal ask = update.getAskPrice();

            // Check price ranges
            assertTrue(bid.compareTo(BigDecimal.valueOf(100.0)) >= 0 &&
                            bid.compareTo(BigDecimal.valueOf(200.0)) <= 0,
                    "Bid price out of range");
            assertTrue(ask.compareTo(BigDecimal.valueOf(100.0)) >= 0 &&
                            ask.compareTo(BigDecimal.valueOf(200.0)) <= 0,
                    "Ask price out of range");

            // Check 2 decimal places
            assertEquals(2, bid.scale(), "Bid price should have 2 decimal places");
            assertEquals(2, ask.scale(), "Ask price should have 2 decimal places");
        });
    }

    @Test
    void testBidAndAskSizeRange() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(5, 100);
        Stream<CertificateUpdate> updates = generator.generateQuotes();

        updates.forEach(update -> {
            int bidSize = update.getBidSize();
            int askSize = update.getAskSize();

            assertTrue(bidSize >= 1000 && bidSize <= 5000, "Bid size out of range");
            assertTrue(askSize >= 1000 && askSize <= 10000, "Ask size out of range");
        });
    }

    @Test
    void testUniqueIsins() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(5, 200);
        Stream<CertificateUpdate> updates = generator.generateQuotes();

        List<String> isins = updates.map(CertificateUpdate::getIsin).collect(Collectors.toList());
        long uniqueCount = isins.stream().distinct().count();

        assertEquals(isins.size(), uniqueCount, "All ISINs should be unique");
    }
}
