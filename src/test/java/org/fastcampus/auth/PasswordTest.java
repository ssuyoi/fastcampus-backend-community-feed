package org.fastcampus.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fastcampus.auth.domain.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PasswordTest {

    @Test
    void givenPassword_whenMatchSamePassword_thenReturnTrue() {
        Password password = Password.createEncryptPassword("password");

        assertTrue(password.matchPassword("password"));
    }


    @Test
    void givenPassword_whenMatchDiffPassword_thenReturnFalse() {
        Password password = Password.createEncryptPassword("password1");

        assertFalse(password.matchPassword("password"));
    }


    @ParameterizedTest
    @NullAndEmptySource
    void givenPasswordIsNull_thenThrowError(String password) {
        assertThrows(IllegalAccessError.class, () -> Password.createEncryptPassword(null));
    }
}
