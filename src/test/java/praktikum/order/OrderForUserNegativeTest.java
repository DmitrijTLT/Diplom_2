package praktikum.order;

import org.junit.Before;
import org.junit.Test;
import praktikum.user.UserData;
import praktikum.user.UserSteps;

import java.util.Arrays;
import java.util.List;

public class OrderForUserNegativeTest {
    private List<String> ingredients;
    private OrderSteps orderSteps = new OrderSteps();
    private UserSteps userSteps = new UserSteps();
    private UserData userData = new UserData();

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

    // Проверяем, что нельзя получить список заказов пользователя без авторизации
    @Test
    public void testGetOrderForUserUnauthorized() {
        orderSteps.getOrderForUser().checkNegativeGetOrderForUserWithoutToken();
    }
}
