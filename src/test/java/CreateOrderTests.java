import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.orders.Order;
import org.example.orders.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.HttpURLConnection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    private final List<String> colour;
    private OrderClient orderClient;
    int track;

    public CreateOrderTests(List<String> colour) {
        this.colour = colour;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void cancelOrder() {
        orderClient.cancelOrder(track);
    }

    @Parameterized.Parameters
    public static Object[][] selectScooterColour() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    //Тесты падают, т.к. не проходит отмена заказа, в ответ приходит код 400, а не 200
    @Test
    @DisplayName("Создание заказа с разными цветами. Позитивный тест")
    @Description("Create order with diff colours test. POST/api/v1/orders")
    public void createOrderWithDiffColours() {
        Order order = new Order(colour);
        ValidatableResponse response = orderClient.createOrder(order);
        track = response.extract().path("track");
        response.assertThat().statusCode(HttpURLConnection.HTTP_CREATED).body("track", is(notNullValue()));
    }
}