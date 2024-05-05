import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.orders.AssertOrder;
import org.example.orders.OrderClient;
import org.junit.Before;
import org.junit.Test;

public class ListOfOrdersTests {
    private OrderClient orderClient;
    private AssertOrder assertOrderVoid = new AssertOrder();

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Проверка получения в теле ответа списка заказов")
    @Description("List of orders test. GET/api/v1/orders")
    public void ListOfOrders() {
        ValidatableResponse response = orderClient.getOrderList();
        assertOrderVoid.getOrderListSuccessfully(response);
    }
}