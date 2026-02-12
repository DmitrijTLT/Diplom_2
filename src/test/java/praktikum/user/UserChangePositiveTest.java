package praktikum.user;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

@Feature("Изменение данных пользователя: Позитивные тесты")
public class UserChangePositiveTest {

    private UserSteps steps = new UserSteps();

    public String accessToken;
    String email = UserData.EMAIL;
    String newEmail = UserData.NEW_EMAIL;
    String password = UserData.PASSWORD;
    String name = UserData.NAME;
    String newName = UserData.NEW_NAME;

    // Регистрируем нового пользователя и получаем его токен
    @Before
    public void setUp() {
        UserRegisterRequest user = new UserRegisterRequest(email, password, name);
        accessToken = steps.registerUser(user).checkRegisterUser();
    }

    @Test
    @DisplayName("Пользователь может изменить email и имя одновременно")
    @Description("Проверка успешного обновления email и имени авторизованным пользователем. Ожидается код 200 и возврат обновлённых данных")
    public void testUserChange() {
        UserChangeRequest user = new UserChangeRequest(newEmail, newName);
        steps.changeUser(user, accessToken).checkChangeUser(newEmail, newName);
    }

    @Test
    @DisplayName("Пользователь может изменить только email")
    @Description("Проверка, что пользователь может обновить только email, оставив имя без изменений. Ожидается код 200 и возврат обновлённых данных")
    public void testUserChangeOnlyEmail() {
        UserChangeRequest user = new UserChangeRequest(newEmail, null);
        steps.changeUser(user, accessToken).checkChangeUser(newEmail, name);
    }

    @Test
    @DisplayName("Пользователь может изменить только имя")
    @Description("Проверка, что пользователь может обновить только имя, оставив email без изменений. Ожидается код 200 и возврат обновлённых данных")
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
