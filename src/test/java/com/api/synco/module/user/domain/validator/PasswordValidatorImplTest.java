package com.api.synco.module.user.domain.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorImplTest {

    private PasswordValidatorImpl passwordValidator;

    @BeforeEach
    public void setup(){
        passwordValidator = new PasswordValidatorImpl();
    }

    @DisplayName("Should return true when password meets all requirements")
    @Test
    public void shouldReturnTrueWhenPasswordIsStrong(){
        // -- arrange
        String password = "Liandra#113";

        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isTrue();
    }

    @DisplayName("Should return false when password has no number")
    @Test
    public void shouldReturnFalseWhenPasswordDontHaveNumber(){
        // -- arrange
        String password = "Lucass#";


        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password has no letter")
    @Test
    public void shouldReturnFalseWhenPasswordDontHaveALetter(){
        // -- arrange
        String password = "#12345678";


        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password is less than 8 characters")
    @Test
    public void shouldReturnFalseWhenPasswordDontHaveEightCharacter(){
        // -- arrange
        String password = "Luca#1";


        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password has no lowercase character")
    @Test
    public void shouldReturnFalseWhenPasswordDontHaveALowerCaseCharacter(){
        // -- arrange
        String password = "LUCAS#1";


        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password is null")
    @Test
    public void shouldReturnFalseWhenPasswordIsNull(){
        // -- act
        var result = passwordValidator.isValid(null);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password has no uppercase character")
    @Test
    public void shouldReturnFalseWhenPasswordDontHaveUppercaseCharacter(){
        // -- arrange
        String password = "lucas#123";

        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password has no special character")
    @Test
    public void shouldReturnFalseWhenPasswordDontHaveSpecialCharacter(){
        // -- arrange
        String password = "Lucas123";

        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password has whitespace")
    @Test
    public void shouldReturnFalseWhenPasswordHasWhitespace(){
        // -- arrange
        String password = "Lucas #123";

        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return false when password exceeds 30 characters")
    @Test
    public void shouldReturnFalseWhenPasswordExceedsMaxLength(){
        // -- arrange
        String password = "Lucas#1234567890123456789012345";

        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isFalse();
    }

    @DisplayName("Should return true when password is at minimum length")
    @Test
    public void shouldReturnTrueWhenPasswordAtMinLength(){
        // -- arrange
        String password = "Lucas#11"; // 8 characters

        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isTrue();
    }

    @DisplayName("Should return true when password is at maximum length")
    @Test
    public void shouldReturnTrueWhenPasswordAtMaxLength(){
        // -- arrange
        String password = "Lucas#12345678901234567890123"; // 30 characters

        // -- act
        var result = passwordValidator.isValid(password);

        // -- assert
        assertThat(result).isTrue();
    }

}