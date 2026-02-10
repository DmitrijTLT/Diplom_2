package praktikum.user;

import org.junit.After;
import org.junit.Test;

// Позитивные тесты для проверки регистрации пользователя
public class UserRegisterPositiveTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();
    private UserData userData = new UserData();

    // Проверяем успешную регистрацию с валидными данными
    @Test
    public void testUserRegister() {
        UserRegisterRequest user = new UserRegisterRequest(userData.email, userData.password, userData.name);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        steps.deleteUser(accessToken).checkDeleteUser();
    }
}
