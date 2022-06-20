package com.example.demo;

import com.example.demo.controller.DemoController;
import com.example.demo.model.CountryCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DemoControllerTest {

    @InjectMocks
    DemoController demoController;

    @Test
    public void shouldReturnTrueWithValidSsn() {
        assertTrue(this.demoController.getSsn("180594-899W", CountryCode.FI).isValidSsn());
    }

    @Test
    public void shouldReturnFalseWithInvalidSsn() {
        this.demoController.getSsn("180594-123M", CountryCode.FI);
    }

}
