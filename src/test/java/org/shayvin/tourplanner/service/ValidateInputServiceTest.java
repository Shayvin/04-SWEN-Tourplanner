package org.shayvin.tourplanner.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateInputServiceTest {

    public ValidateInputService validateInputService;

    @BeforeEach
    void setUp() {
        validateInputService = new ValidateInputService();

    }

    @Test
    void validateInput_Blank() {
        String input = "   ";

        assertFalse(validateInputService.validateInput(input));
    }

    @Test
    void validateInput_Empty() {
        String input = "";

        assertFalse(validateInputService.validateInput(input));
    }

    @Test
    void validateInput_correctInput() {
        String input = "test String";

        assertTrue(validateInputService.validateInput(input));
    }

}