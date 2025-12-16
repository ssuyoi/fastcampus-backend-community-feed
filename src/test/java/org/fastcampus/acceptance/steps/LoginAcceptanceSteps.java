package org.fastcampus.acceptance.steps;

import io.restassured.RestAssured;
import org.fastcampus.auth.application.dto.LoginRequestDto;
import org.fastcampus.auth.application.dto.UserAccessTokenResponseDto;
import org.springframework.http.MediaType;

public class LoginAcceptanceSteps {

    public static String requestLoginGetToken(LoginRequestDto request) {
        UserAccessTokenResponseDto res = RestAssured
            .given()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/login")
            .then()
            .extract()
            .jsonPath()
            .getObject("value", UserAccessTokenResponseDto.class);

        return res.accessToken();
    }

    public static Integer requestLoginGetResponseCode(LoginRequestDto request) {
        return RestAssured
            .given()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/login")
            .then()
            .extract()
            .jsonPath()
            .getObject("code", Integer.class);
    }
}
