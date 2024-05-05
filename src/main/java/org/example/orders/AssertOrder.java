package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class AssertOrder {
    @Step("Успешное получение списка заказов")
    public void getOrderListSuccessfully(ValidatableResponse response) {
        response .assertThat().log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());
    }
}
