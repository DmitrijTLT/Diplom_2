package praktikum.user;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

@Feature("Изменение данных пользователя: Негативные тесты")
public class UserChangeNegativeTest {

    private UserSteps steps = new UserSteps();
//    private UserData userData = new UserData();

    public String accessTokenOne;
    public String accessTokenTwo;
    String email = UserData.EMAIL;
    String newEmail = UserData.NEW_EMAIL;
    String password = UserData.PASSWORD;
    String name = UserData.NAME;
    String newName = UserData.NEW_NAME;

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest userOne = new UserRegisterRequest(email, password, name);
        accessTokenOne = steps.registerUser(userOne).checkRegisterUser();
    }

    @Test
    @DisplayName("Нельзя изменить данные пользователя без авторизации")
    @Description("Проверка, что API возвращает ошибку 401 при попытке изменения профиля без авторизации")
    public void testUserChangeUnauthorized() {
        UserChangeRequest user = new UserChangeRequest(newEmail, newName);
        steps.changeUser(user).checkChangeUserUnauthorized();
    }

    @Test
    @DisplayName("Нельзя изменить email на уже занятый другим пользователем")
    @Description("Проверка, что API возвращает ошибку 403 при попытке установить email, который уже используется другим аккаунтом")
    public void testUserChangeWithEmailAlreadyExists() {
        UserRegisterRequest userTwo = new UserRegisterRequest(newEmail, password, name);
        accessTokenTwo = steps.registerUser(userTwo).checkRegisterUserTwo();

        UserChangeRequest user = new UserChangeRequest(email, newName);
        steps.changeUser(user, accessTokenTwo).checkChangeUserEmailExists();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        if (accessTokenOne != null && accessTokenTwo == null) {
            steps.deleteUser(accessTokenOne).checkDeleteUser();
        } else if (accessTokenOne != null && accessTokenTwo != null) {
            steps.deleteUser(accessTokenOne).checkDeleteUser();
            steps.deleteUser(accessTokenTwo).checkDeleteUser();
        }
    }
}
