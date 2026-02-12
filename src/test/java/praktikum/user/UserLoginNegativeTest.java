package praktikum.user;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;


@Feature("Авторизация пользователя: Негативные тесты")
public class UserLoginNegativeTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest user =new UserRegisterRequest(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неправильным паролем")
    @Description("Проверка, что API возвращает ошибку 401 при вводе неверного пароля, даже если email корректный")
    public void testUserLoginWithInvalidPassword() {
        UserLoginRequest user = new UserLoginRequest(UserData.EMAIL, "invalid");
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    @Test
    @DisplayName("Нельзя авторизоваться с email, которого нет в системе")
    @Description("Проверка, что API возвращает ошибку 401 при попытке входа с несуществующим email")
    public void testUserLoginWithEmailInNotSystem() {
        UserLoginRequest user = new UserLoginRequest(UserData.NEW_EMAIL, UserData.PASSWORD);
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    @Test
    @DisplayName("Нельзя авторизоваться с пустым email")
    @Description("Проверка, что API возвращает ошибку 401 при передаче null в поле email")
    public void testUserLoginWithEmptyEmail() {
        UserLoginRequest user = new UserLoginRequest(null, UserData.PASSWORD);
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    @Test
    @DisplayName("Нельзя авторизоваться с пустым паролем")
    @Description("Проверка, что API возвращает ошибку 401 при передаче null в поле password")
    public void testUserLoginWithEmptyPassword() {
        UserLoginRequest user = new UserLoginRequest(UserData.EMAIL, null);
        steps.loginUser(user, accessToken).checkNegativeLoginUser();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        steps.deleteUser(accessToken).checkDeleteUser();
    }
}
