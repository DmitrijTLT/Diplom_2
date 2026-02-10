package praktikum.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.UserData;
import praktikum.user.UserRegisterRequest;
import praktikum.user.UserSteps;

import java.util.Arrays;
import java.util.List;

public class OrderForUserPositiveTest {
    private String accessToken;
    private List<String> ingredients;
    private OrderSteps orderSteps = new OrderSteps();
    private UserSteps userSteps = new UserSteps();
    private UserData userData = new UserData();
    String email = userData.email;
    String password = userData.password;
    String name = userData.name;
    String bunId;
    String mainId;
    String sauceId;

    // Регистрируем нового пользователя, получаем его токен, формируем список ингредиентов для заказа и создаем заказ
    @Before
    public void setUp() {
        UserRegisterRequest user = new UserRegisterRequest(email, password, name);
        accessToken = userSteps.registerUser(user).checkRegisterUser();

        OrderSteps ingredientsResponse = orderSteps.getAllIngredients();
        bunId = ingredientsResponse.getFirstBunId();
        mainId = ingredientsResponse.getFirstMainId();
        sauceId = ingredientsResponse.getFirstSauceId();
        ingredients = Arrays.asList(bunId, mainId, sauceId);

        OrderCreateRequest order = new OrderCreateRequest(ingredients);
        orderSteps.createOrder(order, accessToken);
    }

    // Проверяем, что можно получить список заказов авторизованного пользователя
    @Test
    public void getOrderForUser() {
        orderSteps.getOrderForUser(accessToken).checkGetOrderForUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        userSteps.deleteUser(accessToken).checkDeleteUser();
    }
}
