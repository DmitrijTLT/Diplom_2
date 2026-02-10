package praktikum.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Негативные тесты для проверки изменения данных пользователя
public class UserChangeNegativeTest {

    private UserSteps steps = new UserSteps();
    private UserData userData = new UserData();

    public String accessTokenOne;
    public String accessTokenTwo;
    String email = userData.email;
    String newEmail = userData.newEmail;
    String password = userData.password;
    String name = userData.name;
    String newName = userData.newName;

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest userOne = new UserRegisterRequest(email, password, name);
        accessTokenOne = steps.registerUser(userOne).checkRegisterUser();
    }

    // Проверяем, что нельзя изменить данные пользователя без авторизации
    @Test
    public void testUserChangeUnauthorized() {
        UserChangeRequest user = new UserChangeRequest(newEmail, newName);
        steps.changeUser(user).checkChangeUserUnauthorized();
    }

    // Проверяем, что нельзя изменить email пользователя, если передать email, который уже используется
    @Test
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
