package praktikum.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import praktikum.user.*;

import java.util.*;


@Feature("Создание заказа: Негативные тесты")
public class OrderCreateNegativeTest {

    private String accessToken;
    private List<String> ingredients;
    private OrderSteps orderSteps = new OrderSteps();
    private UserSteps userSteps = new UserSteps();
//    private final UserData userData = new UserData();
    String email = UserData.EMAIL;
    String password = UserData.PASSWORD;
    String name = UserData.NAME;
    String bunId;
    String mainId;
    String sauceId;

    // Регистрируем нового пользователя, получаем его токен и формируем список ингредиентов для заказа
    @Before
    public void setUp() {
        UserRegisterRequest user = new UserRegisterRequest(email, password, name);
        accessToken = userSteps.registerUser(user).checkRegisterUser();

        OrderSteps ingredientsResponse = orderSteps.getAllIngredients();
        bunId = ingredientsResponse.getFirstBunId();
        mainId = ingredientsResponse.getFirstMainId();
        sauceId = "invalidSauceId";
        ingredients = Arrays.asList(bunId, mainId, sauceId);
    }

    // Проверяем, что нельзя создать заказ без ингредиентов
    @Test
    @DisplayName("Невозможно создать заказ без ингредиентов")
    @Description("Проверка, что API возвращает ошибку при попытке создать заказ без указания ингредиентов")
    public void testCreateOrderWithoutIngredient() {
        OrderCreateRequest order = new OrderCreateRequest();
        orderSteps.createOrder(order).checkNegativeCreateOrderWithoutIngredient();
    }

    // Проверяем, что нельзя создать заказ с невалидным id ингредиента
    @Test
    @DisplayName("Невозможно создать заказ с невалидным id ингредиента")
    @Description("Проверка, что API возвращает ошибку при попытке создать заказ, содержащий ингредиент с несуществующим ID")
    public void testCreateOrderWithInvalidIngredientId() {
        OrderCreateRequest order = new OrderCreateRequest(ingredients);
        orderSteps.createOrder(order).checkNegativeCreateOrderWithInvalidIngredientId();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        userSteps.deleteUser(accessToken).checkDeleteUser();
    }
}
