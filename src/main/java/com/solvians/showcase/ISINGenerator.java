package com.solvians.showcase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ISINGenerator {

    private static final Random random = new Random();
    private static final Map<Character, Integer> conversionTable = new HashMap<>();

    static {
        for (char c = 'A'; c <= 'Z'; c++) {
            conversionTable.put(c, 10 + (c - 'A'));
        }
    }

//    public static void main(String[] args) {
//        System.out.println(conversionTable);
//    }

    public static String generateISINString() {
        StringBuilder sb = new StringBuilder();

        // Add 2 random uppercase letters
        sb.append((char) ('A' + random.nextInt(26)));
        sb.append((char) ('A' + random.nextInt(26)));

        // Add 9 random alphanumeric chars
        for (int i = 0; i < 9; i++) {
            if (random.nextBoolean()) {
                sb.append((char) ('0' + random.nextInt(10))); // digit
            } else {
                sb.append((char) ('A' + random.nextInt(26))); // letter
            }
        }

        // Calculate check digit
        int checkDigit = calculateCheckDigit(sb.toString());
        sb.append(checkDigit);

        return sb.toString();
    }

    public static int calculateCheckDigit(String isinWithoutCheck) {
        StringBuilder numeric = new StringBuilder();
        for (char c : isinWithoutCheck.toCharArray()) {
            if (Character.isDigit(c)) {
                numeric.append(c);
            } else {
                numeric.append(conversionTable.get(c));
            }
        }

        // to calculate from right to left, reversing the numeric string
        String reversed = numeric.reverse().toString();
        int sum = 0;
        for (int i = 0; i < reversed.length(); i++) {
            int digit = Character.getNumericValue(reversed.charAt(i));
            if (i % 2 == 0) { // altering alternate position by multiplying 2
                digit *= 2;
            }
            sum += digit / 10 + digit % 10; //seperating 2 digit sum
        }

        int mod = sum % 10; // to get 4 from 54
        return (mod == 0) ? 0 : (10 - mod); //to get 6 which is nearest multiple of 10 - sum
    }

}
