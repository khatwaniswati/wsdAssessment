package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ISINGeneratorTest {

    @Test
    void testGenerateISINStringLength() {
        String isin = ISINGenerator.generateISINString();
        assertEquals(12, isin.length(), "ISIN length should be 12 characters (2 letters + 9 alphanumerics + 1 check digit)");
    }

    @Test
    void testGenerateISINStringFormat() {
        String isin = ISINGenerator.generateISINString();

        // First 2 chars should be uppercase letters
        assertTrue(Character.isUpperCase(isin.charAt(0)), "First character should be uppercase");
        assertTrue(Character.isUpperCase(isin.charAt(1)), "Second character should be uppercase");

        // Next 9 chars should be letters or digits
        for (int i = 2; i < 11; i++) {
            char c = isin.charAt(i);
            assertTrue(Character.isLetterOrDigit(c), "Character at position " + i + " should be a letter or digit");
        }

        // Last char should be a digit (check digit)
        assertTrue(Character.isDigit(isin.charAt(11)), "Last character should be a digit (check digit)");
    }

    @Test
    void testCalculateCheckDigitCorrectness() {
        String base = "US037833100"; // Example without check digit
        int checkDigit = ISINGenerator.calculateCheckDigit(base);
        assertEquals(5, checkDigit, "Check digit should match expected value"); // You can verify using external ISIN calculator
    }

    @Test
    void testGeneratedISINHasValidCheckDigit() {
        String isin = ISINGenerator.generateISINString();
        String withoutCheck = isin.substring(0, isin.length() - 1);
        int expectedCheckDigit = ISINGenerator.calculateCheckDigit(withoutCheck);
        int actualCheckDigit = Character.getNumericValue(isin.charAt(isin.length() - 1));
        assertEquals(expectedCheckDigit, actualCheckDigit, "Check digit should be valid according to calculation");
    }

    @Test
    void testGenerateMultipleUniqueISINs() {
        Set<String> isinSet = new HashSet<>();
        int total = 100;
        for (int i = 0; i < total; i++) {
            String isin = ISINGenerator.generateISINString();
            isinSet.add(isin);
        }
        assertEquals(total, isinSet.size(), "All generated ISINs should be unique"); // Highly likely but not guaranteed
    }
}
