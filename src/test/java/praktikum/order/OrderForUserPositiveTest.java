package praktikum.order;

import io.qameta.allure.*;
import org.junit.*;
import praktikum.user.*;
import io.qameta.allure.junit4.DisplayName;

import java.util.*;

@Feature("Получение заказов пользователя: Позитивные тесты")
public class OrderForUserPositiveTest {
    private String accessToken;
    private List<String> ingredients;
    private OrderSteps orderSteps = new OrderSteps();
    private UserSteps userSteps = new UserSteps();
    String email = UserData.EMAIL;
    String password = UserData.PASSWORD;
    String name = UserData.NAME;
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

    @Test
    @DisplayName("Авторизованный пользователь может получить свои заказы")
    @Description("Проверка, что авторизованный пользователь может успешно получить список своих заказов через API")
    public void getOrderForUser() {
        orderSteps.getOrderForUser(accessToken).checkGetOrderForUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        userSteps.deleteUser(accessToken).checkDeleteUser();
    }
}
