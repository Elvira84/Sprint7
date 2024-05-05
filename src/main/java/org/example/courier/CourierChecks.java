package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class CourierChecks {
    @Step("Успешное получение логина курьера")
    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
        return id;
    }

    @Step("Успешное создание курьера")
    public void createdSuccessfully(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }


    @Step("Успешное удаление курьера")
    public ValidatableResponse deletedSuccessfully(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("ok", is(true));


    }
}
