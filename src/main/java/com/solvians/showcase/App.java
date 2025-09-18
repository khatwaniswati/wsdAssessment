package com.solvians.showcase;

import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
       if(args.length<2)
           throw new RuntimeException("Expect at least number of threads and number of quotes. But got: " + args);

        if (args.length >= 2) {
            int threads = Integer.parseInt(args[0]);
            int quotes = Integer.parseInt(args[1]);

            CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(threads, quotes);
            certificateUpdateGenerator.generateQuotes().forEachOrdered(certificateUpdate ->
                    System.out.println(certificateUpdate.toString()));

            System.out.println("Program Ended after generating "+args[1]+" certificates!");
        }
    }
}
