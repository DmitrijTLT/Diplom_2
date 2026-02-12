package praktikum.order;

import io.qameta.allure.*;
import org.junit.*;
import praktikum.user.*;
import io.qameta.allure.junit4.DisplayName;

import java.util.Arrays;
import java.util.List;

@Feature("Получение заказов пользователя: Негативные тесты")
public class OrderForUserNegativeTest {
    private List<String> ingredients;
    private OrderSteps orderSteps = new OrderSteps();
//    private UserSteps userSteps = new UserSteps();
//    private UserData userData = new UserData();

    String bunId;
    String mainId;
    String sauceId;

    // Регистрируем нового пользователя, получаем его токен, формируем список ингредиентов для заказа и создаем заказ
    @Before
    public void setUp() {
        OrderSteps ingredientsResponse = orderSteps.getAllIngredients();
        bunId = ingredientsResponse.getFirstBunId();
        mainId = ingredientsResponse.getFirstMainId();
        sauceId = ingredientsResponse.getFirstSauceId();
        ingredients = Arrays.asList(bunId, mainId, sauceId);

        OrderCreateRequest order = new OrderCreateRequest(ingredients);
        orderSteps.createOrder(order).checkCreateOrder();
    }

    @Test
    @DisplayName("Невозможно получить заказы пользователя без авторизации")
    @Description("Проверка, что API возвращает ошибку 401 при попытке получить список заказов без авторизации")
    public void testGetOrderForUserUnauthorized() {
        orderSteps.getOrderForUser().checkNegativeGetOrderForUserWithoutToken();
    }
}
