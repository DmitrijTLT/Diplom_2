package praktikum.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import praktikum.user.*;

import java.util.Arrays;
import java.util.List;

@Feature("Создание заказа: Позитивные тесты")
public class OrderCreatePositiveTest {

    private String accessToken;
    private List<String> ingredients;
    private OrderSteps orderSteps = new OrderSteps();
    private UserSteps userSteps = new UserSteps();
//    private UserData userData = new UserData();
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
        sauceId = ingredientsResponse.getFirstSauceId();
        ingredients = Arrays.asList(bunId, mainId, sauceId);
    }

    @Test
    @DisplayName("Авторизованный пользователь может создать заказ")
    @Description("Проверка, что авторизованный пользователь может успешно создать заказ с валидными ингредиентами")
    public void testCreateOrder() {
        OrderCreateRequest order = new OrderCreateRequest(ingredients);
        orderSteps.createOrder(order, accessToken).checkCreateOrder();
    }

    @Test
    @DisplayName("Неавторизованный пользователь может создать заказ")
    @Description("Проверка, что пользователь без авторизации может успешно создать заказ с валидными ингредиентами")
    public void testCreateOrderUnauthorized() {
        OrderCreateRequest order = new OrderCreateRequest(ingredients);
        orderSteps.createOrder(order).checkCreateOrder();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        userSteps.deleteUser(accessToken).checkDeleteUser();
    }
}
