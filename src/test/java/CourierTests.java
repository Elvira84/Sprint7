import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.courier.Courier;
import org.example.courier.CourierChecks;
import org.example.courier.CourierClient;
import org.example.courier.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class CourierTests {

    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    int courierId;

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.deleteCourier(courierId);
            check.deletedSuccessfully(response);
        }
    }

    @Test
    @DisplayName("Создание курьера со всеми обязательными полями")
    @Description("Courier created test. POST/api/v1/courier")
    public void courierCreated() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);

        courierId = check.loggedInSuccessfully(loginResponse);

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Создание курьера без логина. Негативный тест.")
    @Description("Courier creation without login. Negative test. POST/api/v1/courier")
    public void courierCreationWithoutLogin() {
        Courier courier = new Courier("", "1234", "ninja");

        ValidatableResponse response = client.createCourier(courier);

        response.statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля. Негативный тест.")
    @Description("Courier creation without password. Negative test. POST/api/v1/courier")
    public void courierCreationWithoutPassword() {
        Courier courier = new Courier("abab", "", "ninja");

        ValidatableResponse response = client.createCourier(courier);

        response.statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    // Тест падает,т.к. курьер создается, хотя должна приходить ошибка.
    @Test
    @DisplayName("Создание курьера без имени. Негативный тест.")
    @Description("Courier creation without firstName. Negative test. POST/api/v1/courier")
    public void courierCreationWithoutFirstName() {
        Courier courier = new Courier("abab", "1234", "");

        ValidatableResponse response = client.createCourier(courier);

        response.statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    //Тест падает, т.к. в апи-доке указан один текст ошибки, а приходит другой
    @Test
    @DisplayName("Невозможно создать двух одинаковых курьеров. Негативный тест.")
    @Description("Can not create two identical couriers. Negative test. POST/api/v1/courier")
    public void canNotCreateTwoIdenticalCouriersTest() {
        Courier existingCourier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(existingCourier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(existingCourier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        Courier duplicateCourier = new Courier(existingCourier.getLogin(), existingCourier.getPassword(), existingCourier.getFirstName());
        ValidatableResponse duplicateCreateResponse = client.createCourier((duplicateCourier));

        duplicateCreateResponse.statusCode(HttpURLConnection.HTTP_CONFLICT)
                .and()
                .body("message", is("Этот логин уже используется"));

    }

}