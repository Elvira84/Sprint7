package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;

import java.net.HttpURLConnection;


public class OrderClient extends Client {

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post("/orders")
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED);
    }

    @Step("Отмена созданного заказа")
    public ValidatableResponse cancelOrder(int track) {
        return spec()
                .body("{\"track\":" + track + "}")
                .when()
                .put("/orders/cancel")
                .then()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Получение списка заказа")
    public ValidatableResponse getOrderList() {
        return spec()
                .when()
                .get("/orders")
                .then()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
}
