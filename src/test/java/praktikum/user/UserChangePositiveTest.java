package praktikum.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Позитивные тесты для проверки изменения данных пользователя
public class UserChangePositiveTest {

    private UserSteps steps = new UserSteps();
    private UserData userData = new UserData();

    public String accessToken;
    String email = userData.email;
    String newEmail = userData.newEmail;
    String password = userData.password;
    String name = userData.name;
    String newName = userData.newName;

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest user = new UserRegisterRequest(email, password, name);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    // Проверяем успешное изменение пользователя с изменением email и name
    @Test
    public void testUserChange() {
        UserChangeRequest user = new UserChangeRequest(newEmail, newName);
        steps.changeUser(user, accessToken).checkChangeUser(newEmail, newName);
    }

    // Проверяем успешное изменение пользователя с изменением только email
    @Test
    public void testUserChangeOnlyEmail() {
        UserChangeRequest user = new UserChangeRequest(newEmail, null);
        steps.changeUser(user, accessToken).checkChangeUser(newEmail, name);
    }

    // Проверяем успешное изменение пользователя с изменением только name
    @Test
    public void testUserChangeOnlyName() {
        UserChangeRequest user = new UserChangeRequest(null, newName);
        steps.changeUser(user, accessToken).checkChangeUser(email, newName);
    }

    // Удаляем зарегистрированного пользователя по токену
    @After
    public void tearDown() {
        steps.deleteUser(accessToken).checkDeleteUser();
    }
}
