package com.solvians.showcase;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class CertificateUpdateCallable implements Callable<CertificateUpdate> {

    @Override
    public CertificateUpdate call() {

        long timestamp = System.currentTimeMillis();

        String isin = ISINGenerator.generateISINString();

        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Bid Price: 100.00 ≤ bidPrice ≤ 200.00, 2 decimal places
        double bidPrice = random.nextDouble(100.0, 200.01);
        bidPrice = Math.round(bidPrice * 100.0) / 100.0;

        // Bid Size: 1000 ≤ bidSize ≤ 5000
        int bidSize = random.nextInt(1000, 5001);

        // Ask Price: 100.00 ≤ askPrice ≤ 200.00, 2 decimal places
        double askPrice = random.nextDouble(100.0, 200.01);
        askPrice = Math.round(askPrice * 100.0) / 100.0;

        // Ask Size: 1000 ≤ askSize ≤ 10000
        int askSize = random.nextInt(1000, 10001);

        return new CertificateUpdate(timestamp, isin, bidPrice, bidSize, askPrice, askSize);
    }
}
