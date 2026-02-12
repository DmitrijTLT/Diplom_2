package praktikum.user;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

@Feature("Регистрация пользователя: Позитивные тесты")
public class UserRegisterPositiveTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();
//    private UserData userData = new UserData();

    @Test
    @DisplayName("Пользователь может зарегистрироваться с валидными данными")
    @Description("Проверка успешной регистрации пользователя с корректным email, паролем и именем. Ожидается код 200 и получение токена")
    public void testUserRegister() {
        UserRegisterRequest user = new UserRegisterRequest(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        steps.deleteUser(accessToken).checkDeleteUser();
    }
}
