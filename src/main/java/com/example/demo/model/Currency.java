package com.example.demo.model;

public enum Currency {

    EUR("EUR"), SEK("SEK"), USD("USD");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Currency findByName(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.getValue().equalsIgnoreCase(value)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Currency enum not found with value: "+value);
    }
}
