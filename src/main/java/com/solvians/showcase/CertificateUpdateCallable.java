package com.solvians.showcase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class CertificateUpdateCallable implements Callable<CertificateUpdate> {

    @Override
    public CertificateUpdate call() {

        long timestamp = System.currentTimeMillis();

        String isin = ISINGenerator.generateISINString();

        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Bid Price: 100.00 ≤ bidPrice ≤ 200.00, 2 decimal places
        BigDecimal bidPrice = BigDecimal.valueOf(random.nextDouble(100.0, 200.01))
                .setScale(2, RoundingMode.HALF_UP);

        // Bid Size: 1000 ≤ bidSize ≤ 5000
        int bidSize = random.nextInt(1000, 5001);

        // Ask Price: 100.00 ≤ askPrice ≤ 200.00, 2 decimal places
        BigDecimal askPrice = BigDecimal.valueOf(random.nextDouble(100.0, 200.01))
                .setScale(2, RoundingMode.HALF_UP);

        // Ask Size: 1000 ≤ askSize ≤ 10000
        int askSize = random.nextInt(1000, 10001);

        return new CertificateUpdate(timestamp, isin, bidPrice, bidSize, askPrice, askSize);
    }
}
