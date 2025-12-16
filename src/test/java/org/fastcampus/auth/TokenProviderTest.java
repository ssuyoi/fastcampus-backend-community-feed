package org.fastcampus.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.fastcampus.auth.domain.TokenProvider;
import org.junit.jupiter.api.Test;

class TokenProviderTest {

    private final String secretKey = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
    private final TokenProvider tokenProvider = new TokenProvider(secretKey);

    @Test
    void givenValidUserAndRole_whenCreateToken_thenReturnValidToken() {
        // given
        Long userId = 1L;
        String role = "ADMIN";

        // when
        String token = tokenProvider.createToken(userId, role);

        // then
        assertNotNull(token);
        assertEquals(userId, tokenProvider.getUserId(token));
        assertEquals(role, tokenProvider.getUserRole(token));
    }

    @Test
    void givenInvalidUserAndRole_whenGetUserId_thenThrowError() {
        // given
        String invalidToken = "invalidToken";

        assertThrows(Exception.class, () -> tokenProvider.getUserId(invalidToken));
    }
}
