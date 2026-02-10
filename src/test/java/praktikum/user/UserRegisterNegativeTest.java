package praktikum.user;

import org.junit.After;
import org.junit.Test;

// Негативные тесты для проверки регистрации пользователя
public class UserRegisterNegativeTest {

    public String accessToken;

    private UserSteps steps = new UserSteps();
    private UserData userData = new UserData();

    // Проверяем, что нельзя зарегистрировать пользователя, если передать email, который уже используется
    @Test
    public void testUserRegisterWithEmailAlreadyExists() {
        UserRegisterRequest userOne = new UserRegisterRequest(userData.email, userData.password, userData.name);
        accessToken = steps.registerUser(userOne).checkRegisterUser();

        UserRegisterRequest userTwo = new UserRegisterRequest(userData.email, userData.password, userData.name);
        steps.registerUser(userTwo).checkRegisterUserAlreadyExists();
    }

    // Проверяем, что нельзя зарегистрироваться без email
    @Test
    public void testUserRegisterWithoutEmail() {
        UserRegisterRequest user = new UserRegisterRequest(null, userData.password, userData.name);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Проверяем, что нельзя зарегистрироваться c пустым email
    @Test
    public void testUserRegisterWithEmptyEmail() {
        UserRegisterRequest user = new UserRegisterRequest("", userData.password, userData.name);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Проверяем, что нельзя зарегистрироваться без password
    @Test
    public void testUserRegisterWithoutPassword() {
        UserRegisterRequest user = new UserRegisterRequest(userData.email, null, userData.name);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Проверяем, что нельзя зарегистрироваться c пустым password
    @Test
    public void testUserRegisterWithEmptyPassword() {
        UserRegisterRequest user = new UserRegisterRequest(userData.email, "", userData.name);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Проверяем, что нельзя зарегистрироваться без name
    @Test
    public void testUserRegisterWithoutName() {
        UserRegisterRequest user = new UserRegisterRequest(userData.email, userData.password, null);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Проверяем, что нельзя зарегистрироваться c пустым name
    @Test
    public void testUserRegisterWithEmtyName() {
        UserRegisterRequest user = new UserRegisterRequest(userData.email, userData.password, "");
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Проверяем, что нельзя зарегистрироваться без тела запроса
    @Test
    public void testUserRegisterWithoutParametersInBody() {
        UserRegisterRequest user = new UserRegisterRequest(null, null, null);
        steps.registerUser(user).checkRegisterUserMissingFields();
    }

    // Проверяем, что нельзя зарегистрироваться c пустыми параметрами в теле запроса
    @Test
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
