package praktikum.user;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

@Feature("Регистрация пользователя: Негативные тесты")
public class UserRegisterNegativeTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();
//    private UserData userData = new UserData();

    // Проверяем, что нельзя зарегистрировать пользователя, если передать email, который уже используется
    @Test
    @DisplayName("Нельзя зарегистрировать пользователя с уже существующим email")
    @Description("Проверка, что API возвращает ошибку 403 при попытке зарегистрировать пользователя с email, который уже используется")
    public void testUserRegisterWithEmailAlreadyExists() {
        UserRegisterRequest userOne = new UserRegisterRequest(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);
        accessToken = steps.registerUser(userOne).checkRegisterUser();

        UserRegisterRequest userTwo = new UserRegisterRequest(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);
        steps.registerUser(userTwo).checkRegisterUserAlreadyExists();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя без указания email")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать поле email: null")
    public void testUserRegisterWithoutEmail() {
        UserRegisterRequest user = new UserRegisterRequest(null, UserData.PASSWORD, UserData.NAME);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя с пустым email")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать поле email: ''")
    public void testUserRegisterWithEmptyEmail() {
        UserRegisterRequest user = new UserRegisterRequest("", UserData.PASSWORD, UserData.NAME);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя без пароля")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать поле password: null")
    public void testUserRegisterWithoutPassword() {
        UserRegisterRequest user = new UserRegisterRequest(UserData.EMAIL, null, UserData.NAME);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя с пустым паролем")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать поле password: ''")
    public void testUserRegisterWithEmptyPassword() {
        UserRegisterRequest user = new UserRegisterRequest(UserData.EMAIL, "", UserData.NAME);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя без имени")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать поле name: null")
    public void testUserRegisterWithoutName() {
        UserRegisterRequest user = new UserRegisterRequest(UserData.EMAIL, UserData.PASSWORD, null);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя с пустым именем")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать поле name:''")
    public void testUserRegisterWithEmtyName() {
        UserRegisterRequest user = new UserRegisterRequest(UserData.EMAIL, UserData.PASSWORD, "");
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя без тела запроса")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать все поля = null")
    public void testUserRegisterWithoutParametersInBody() {
        UserRegisterRequest user = new UserRegisterRequest(null, null, null);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя с пустыми параметрами")
    @Description("Проверка, что API возвращает ошибку 400, если в запросе указать все поля = ''")
    public void testUserRegisterWithEmtyBody() {
        UserRegisterRequest user = new UserRegisterRequest("", "", "");
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        if (accessToken != null) {
            steps.deleteUser(accessToken).checkDeleteUser();
        }
    }
}
