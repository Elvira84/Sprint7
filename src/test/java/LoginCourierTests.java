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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTests {
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
    @DisplayName("Курьер может авторизоваться при передаче всех обязательных полей")
    @Description("Сourier can login test. POST/api/v1/courier/login")
    public void courierCanLogin() {
        Courier courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        int id = check.loggedInSuccessfully(loginResponse);

        assertNotEquals(0, id);
    }

    @Test
    @DisplayName("Система вернет ошибку, если передать только логин")
    @Description("Login field is missing. Negative test. POST/api/v1/courier/login")
    public void loginFieldIsMissing() {
        CourierCredentials missingLogin = new CourierCredentials("", "1234");
        ValidatableResponse loginResponse = client.loginCourier(missingLogin);
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Система вернет ошибку, если передать только пароль")
    @Description("Password field is missing. Negative test. POST/api/v1/courier/login")
    public void passwordFfieldIsMissing() {
        CourierCredentials missingPassword = new CourierCredentials("abab", "");
        ValidatableResponse passwordResponse = client.loginCourier(missingPassword);
        passwordResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Система вернет ошибку, если передать неверный логин")
    @Description("Invalid login. Negative test. POST/api/v1/courier/login")
    public void invalidLogin() {
        Courier courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        int id = check.loggedInSuccessfully(loginResponse);
        courierId = id;

        assertNotEquals(0, id);

        CourierCredentials incorrectCredentials = new CourierCredentials(courier.getLogin() + "1", courier.getPassword());
        ValidatableResponse invalidLoginResponse = client.loginCourier(incorrectCredentials);
        invalidLoginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Система вернет ошибку, если передать неверный пароль")
    @Description("Invalid password. Negative test. POST/api/v1/courier/login")
    public void invalidPassword() {
        Courier courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        int id = check.loggedInSuccessfully(loginResponse);
        courierId = id;

        assertNotEquals(0, id);

        CourierCredentials incorrectCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword() + "1");
        ValidatableResponse invalidPasswordResponse = client.loginCourier(incorrectCredentials);
        invalidPasswordResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));

    }


}