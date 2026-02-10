package praktikum.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Негативные тесты для проверки авторизации пользователя
public class UserLoginNegativeTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();
    private UserData userData = new UserData();

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest user =new UserRegisterRequest(userData.email, userData.password, userData.name);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    // Проверяем, что нельзя авторизоваться с неправильным паролем
    @Test
    public void testUserLoginWithInvalidPassword() {
        UserLoginRequest user = new UserLoginRequest(userData.email, "invalid");
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    // Проверяем, что нельзя авторизоваться  с несуществующим email
    @Test
    public void testUserLoginWithEmailInNotSystem() {
        UserLoginRequest user = new UserLoginRequest("m@m.mm", userData.password);
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    // Проверяем, что нельзя авторизоваться с пустым email
    @Test
    public void testUserLoginWithEmptyEmail() {
        UserLoginRequest user = new UserLoginRequest(null, userData.password);
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    // Проверяем, что нельзя авторизоваться с пустым паролем
    @Test
    public void testUserLoginWithEmptyPassword() {
        UserLoginRequest user = new UserLoginRequest(userData.email, null);
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        steps.deleteUser(accessToken).checkDeleteUser();
    }
}
