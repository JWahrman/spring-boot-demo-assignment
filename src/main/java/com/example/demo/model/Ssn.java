package com.example.demo.model;


import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Ssn {

    private final static int VALID_LENGTH = 11;
    private final static int MIN_INDIVIDUAL_NUMBER = 2;
    private final static int MAX_INDIVIDUAL_NUMBER = 899;
    private final static int CONTROL_CHARACTER_DIVIDER = 31;
    private final static List<String> VALID_CENTURY_CHARACTERS = Arrays.asList("+", "-", "A");
    private final static String DATE_FORMAT = "ddMMyy";
    private final String value;

    public Ssn(String value) {
        Assert.hasLength(value, "Ssn value must not be null or empty");
        Assert.isTrue(value.length() == VALID_LENGTH, "Ssn value length must be 12");
        this.value = this.validate(value);
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String value) {
        try {
            new Ssn(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String validate(String value) {

        String birthday = value.substring(0, 6);
        String centurySignChar = value.substring(6, 7);
        String individualNumber = value.substring(7, 10);
        String controlCharacter = value.substring(10, 11);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate.parse(birthday, formatter);

        BigDecimal convertedIndividualNumber = this.convertStringToBigDecimal(individualNumber);

        if (!VALID_CENTURY_CHARACTERS.contains(centurySignChar)) {
            throw new IllegalArgumentException("Invalid SSN century sign: "+centurySignChar+" in value: "+value);
        }

        if (convertedIndividualNumber.intValue() < MIN_INDIVIDUAL_NUMBER || convertedIndividualNumber.intValue() > MAX_INDIVIDUAL_NUMBER) {
            throw new IllegalArgumentException("Invalid SSN individual number: "+individualNumber+" in value: "+value);
        }

        if (!this.validateControlCharacter(birthday, individualNumber, controlCharacter)) {
            throw new IllegalArgumentException("Invalid SSN control character: "+controlCharacter+" in value: "+value);
        }

        return value;
    }

    private boolean validateControlCharacter(String birthday, String individualNumber, String controlCharacter) {

        Map<String, String> controlCharacters = this.getControlCharacters();

        BigDecimal identityCode = this.convertStringToBigDecimal(birthday+individualNumber);

        BigDecimal dividedValue = BigDecimal.valueOf(identityCode.doubleValue() / CONTROL_CHARACTER_DIVIDER);
        BigDecimal decimalPart = dividedValue.subtract(BigDecimal.valueOf(dividedValue.intValue()));
        int controlCharacterKey = decimalPart.multiply(BigDecimal.valueOf(CONTROL_CHARACTER_DIVIDER)).setScale(0, RoundingMode.HALF_UP).intValue();

        return controlCharacter.equals(controlCharacters.get(controlCharacterKey + ""));
    }

    private BigDecimal convertStringToBigDecimal(String value) {
        return BigDecimal.valueOf(Double.parseDouble(value));
    }

    private Map<String, String> getControlCharacters() {
        return Map.ofEntries(
                entry("0", "0"),
                entry("1", "1"),
                entry("2", "2"),
                entry("3", "3"),
                entry("4", "4"),
                entry("5", "5"),
                entry("6", "6"),
                entry("7", "7"),
                entry("8", "8"),
                entry("9", "9"),
                entry("10", "A"),
                entry("11", "B"),
                entry("12", "C"),
                entry("13", "D"),
                entry("14", "E"),
                entry("15", "F"),
                entry("16", "H"),
                entry("17", "J"),
                entry("18", "K"),
                entry("19", "L"),
                entry("20", "M"),
                entry("21", "N"),
                entry("22", "P"),
                entry("23", "R"),
                entry("24", "S"),
                entry("25", "T"),
                entry("26", "U"),
                entry("27", "V"),
                entry("28", "W"),
                entry("29", "X"),
                entry("30", "Y")
        );
    }
}
