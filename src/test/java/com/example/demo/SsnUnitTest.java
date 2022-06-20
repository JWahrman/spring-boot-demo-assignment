package com.example.demo;

import com.example.demo.model.Ssn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SsnUnitTest {

    @TestFactory
    public List<DynamicTest> testSsnWithValidValues() {
        return Arrays.asList(
                dynamicTest("SSN with valid value: 180594-899W", () -> Assertions.assertDoesNotThrow(() -> new Ssn("180594-899W"))),
                dynamicTest("SSN with valid value: 131099+1561", () -> Assertions.assertDoesNotThrow(() -> new Ssn("131099+1561"))),
                dynamicTest("SSN with valid value: 151215A656C", () -> Assertions.assertDoesNotThrow(() -> new Ssn("151215A656C")))
        );
    }

    @TestFactory
    public List<DynamicTest> testSsnWithInvalidValues() {
        return Arrays.asList(
                dynamicTest("SSN with null value", () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new Ssn(null))),
                dynamicTest("SSN with too short value", () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new Ssn("010170"))),
                dynamicTest("SSN with too long value", () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new Ssn("010170-12345"))),
                dynamicTest("SSN with invalid date", () -> Assertions.assertThrows(Exception.class, () -> new Ssn("320170-123M"))),
                dynamicTest("SSN with invalid century character: B", () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new Ssn("010170B123M"))),
                dynamicTest("SSN with invalid individual number: 001", () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new Ssn("010170-001M"))),
                dynamicTest("SSN with invalid individual number: 900", () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new Ssn("010170-900M"))),
                dynamicTest("SSN with invalid control character: 010170-855T", () -> Assertions.assertThrows(IllegalArgumentException.class, () -> new Ssn("010170-855T")))
        );
    }
}
