package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SsnValidationResponse {

    private final boolean validSsn;

    public SsnValidationResponse(boolean validSsn) {
        this.validSsn = validSsn;
    }

    @JsonProperty("valid_ssn")
    public boolean isValidSsn() {
        return validSsn;
    }
}
