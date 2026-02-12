package praktikum.user;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

@Feature("Авторизация пользователя: Позитивные тесты")
public class UserLoginPositiveTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();
//    private UserData userData = new UserData();

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest user =new UserRegisterRequest(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    @Test
    @DisplayName("Пользователь может успешно авторизоваться с валидными данными")
    @Description("Проверка, что пользователь может войти в систему с корректным email и паролем. Ожидается код 200 и получение данных пользователя с токеном")
    public void testUserLogin() {
        UserLoginRequest user = new UserLoginRequest(UserData.EMAIL, UserData.PASSWORD);
        steps.loginUser(user, accessToken).checkLoginUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        steps.deleteUser(accessToken).checkDeleteUser();
    }
}
