package praktikum.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.user.UserData;
import praktikum.user.UserRegisterRequest;
import praktikum.user.UserSteps;

import java.util.Arrays;
import java.util.List;

//Негативные тесты для создания заказа
public class OrderCreateNegativeTest {

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
    public void testCreateOrderWithoutIngredient() {
        OrderCreateRequest order = new OrderCreateRequest();
        orderSteps.createOrder(order).checkNegativeCreateOrderWithoutIngredient();
    }

    // Проверяем, что нельзя создать заказ с невалидным id ингредиента
    @Test
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
