package praktikum.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Позитивные тесты для проверки авторизации пользователя
public class UserLoginPositiveTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();
    private UserData userData = new UserData();

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest user =new UserRegisterRequest(userData.email, userData.password, userData.name);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    // Проверяем успешную авторизацию с валидными данными
    @Test
    public void testUserLogin() {
        UserLoginRequest user = new UserLoginRequest(userData.email, userData.password);
        steps.loginUser(user, accessToken).checkLoginUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        steps.deleteUser(accessToken).checkDeleteUser();
    }
}
