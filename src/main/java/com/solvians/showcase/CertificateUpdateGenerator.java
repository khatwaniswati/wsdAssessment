package com.solvians.showcase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class CertificateUpdateGenerator {
    private final int threads;
    private final int quotes;

    public CertificateUpdateGenerator(int threads, int quotes) {
        this.threads = threads;
        this.quotes = quotes;
    }

    public Stream<CertificateUpdate> generateQuotes() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<Future<CertificateUpdate>> futures = new ArrayList<>();

        for (int i = 0; i < quotes; i++) {
            futures.add(executor.submit(new CertificateUpdateCallable()));
        }

        executor.shutdown(); //once all the submitted tasks are completed

        List<CertificateUpdate> updateList = new ArrayList<CertificateUpdate>();
        for (Future<CertificateUpdate> f : futures) {
            try {
                updateList.add(f.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return updateList.parallelStream();
    }
}
