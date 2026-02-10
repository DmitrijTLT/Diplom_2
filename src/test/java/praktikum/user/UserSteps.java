package praktikum.user;

import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

// Шаги для тестирования API пользователей
public class UserSteps {

    private UserApi userApi = new UserApi();
    private UserData userData = new UserData();
    private ValidatableResponse response;

    String messageUserAlreadyExists = "User already exists";
    String messageNoRequiredField = "Email, password and name are required fields";
    String messageIncorrectEmailOrPassword = "email or password are incorrect";
    String messageShouldBeAuthorised = "You should be authorised";
    String messageEmailAlreadyExists = "User with such email already exists";

    // Регистрация пользователя
    public UserSteps registerUser(UserRegisterRequest user) {
        response = userApi.registerUser(user).then();
        return this;
    }

    // Проверка успешной регистрации (возвращает accessToken)
    public String checkRegisterUser() {
        response.assertThat().statusCode(SC_OK);
        UserRegisterResponse userRegisterResponseFromApi = response.extract().body().as(UserRegisterResponse.class);
        assertTrue("Ответ сервера должен содержать success = true", userRegisterResponseFromApi.isSuccess());
        assertNotNull("Ответ сервера должен содержать объект user", userRegisterResponseFromApi.getUser());
        assertEquals("Поле email в объекте user должно совпадать с: " + userData.email, userData.email.toLowerCase(), userRegisterResponseFromApi.getUser().getEmail().toLowerCase());
        assertEquals("Поле name в объекте user должно совпадать с: " + userData.name, userData.name, userRegisterResponseFromApi.getUser().getName());
        assertNotNull("Ответ сервера должен содержать accessToken", userRegisterResponseFromApi.getAccessToken());
        assertTrue("Поле accessToken должно начинаться со слова 'Bearer'", userRegisterResponseFromApi.getAccessToken().startsWith("Bearer "));
        assertNotNull("Ответ сервера должен содержать refreshToken", userRegisterResponseFromApi.getRefreshToken());
        return userRegisterResponseFromApi.getAccessToken();
    }

    // Проверка регистрации с новым email
    public String checkRegisterUserTwo() {
        response.assertThat().statusCode(SC_OK);
        UserRegisterResponse userRegisterResponseFromApi = response.extract().body().as(UserRegisterResponse.class);
        assertTrue("Ответ сервера должен содержать success = true", userRegisterResponseFromApi.isSuccess());
        assertNotNull("Ответ сервера должен содержать объект user", userRegisterResponseFromApi.getUser());
        assertEquals("Поле email в объекте user должно совпадать с: " + userData.newEmail, userData.newEmail.toLowerCase(), userRegisterResponseFromApi.getUser().getEmail().toLowerCase());
        assertEquals("Поле name в объекте user должно совпадать с: " + userData.name, userData.name, userRegisterResponseFromApi.getUser().getName());
        assertNotNull("Ответ сервера должен содержать accessToken", userRegisterResponseFromApi.getAccessToken());
        assertTrue("Поле accessToken должно начинаться со слова 'Bearer'", userRegisterResponseFromApi.getAccessToken().startsWith("Bearer "));
        assertNotNull("Ответ сервера должен содержать refreshToken", userRegisterResponseFromApi.getRefreshToken());
        return userRegisterResponseFromApi.getAccessToken();
    }


    // Проверка неуспешной регистрации пользователя при попытке зарегистрироваться с уже занятым email
    public UserSteps checkRegisterUserAlreadyExists() {
        response.assertThat().statusCode(SC_FORBIDDEN);
        UserErrorResponse error = response.extract().body().as(UserErrorResponse.class);
        assertFalse(error.isSuccess());
        assertEquals(messageUserAlreadyExists, error.getMessage());
        return this;
    }

    // Проверка неуспешной регистрации пользователя с пустыми или отсутствующими обязательными полями
    public UserSteps checkRegisterUserMissingFields() {
        response.assertThat().statusCode(SC_FORBIDDEN);
        UserErrorResponse error = response.extract().body().as(UserErrorResponse.class);
        assertFalse(error.isSuccess());
        assertEquals(messageNoRequiredField, error.getMessage());
        return this;
    }

    // Авторизация пользователя
    public UserSteps loginUser(UserLoginRequest user, String token) {
        response = userApi.loginUser(user, token).then();
        return this;
    }

    // Проверка успешной авторизации
    public String checkLoginUser() {
        response.assertThat().statusCode(SC_OK);
        UserRegisterResponse userLoginResponseFromApi = response.extract().body().as(UserRegisterResponse.class);
        assertTrue("Ответ сервера должен содержать success = true", userLoginResponseFromApi.isSuccess());
        assertNotNull("Ответ сервера должен содержать объект user", userLoginResponseFromApi.getUser());
        assertEquals("Поле email в объекте user должно совпадать с: " + userData.email, userData.email.toLowerCase(), userLoginResponseFromApi.getUser().getEmail().toLowerCase());
        assertEquals("Поле name в объекте user должно совпадать с: " + userData.name, userData.name, userLoginResponseFromApi.getUser().getName());
        assertNotNull("Ответ сервера должен содержать accessToken", userLoginResponseFromApi.getAccessToken());
        assertTrue("Поле accessToken должно начинаться со слова 'Bearer'", userLoginResponseFromApi.getAccessToken().startsWith("Bearer "));
        assertNotNull("Ответ сервера должен содержать refreshToken", userLoginResponseFromApi.getRefreshToken());
        return userLoginResponseFromApi.getAccessToken();
    }

    // Проверка неуспешной авторизации
    public UserSteps checkNegativeLoginUser() {
        response.assertThat().statusCode(SC_UNAUTHORIZED);
        UserErrorResponse userErrorResponseFromApi = response.extract().body().as(UserErrorResponse.class);
        assertFalse("Ответ сервера должен содержать success = false", userErrorResponseFromApi.isSuccess());
        assertEquals("Некорректное сообщение об ошибке", messageIncorrectEmailOrPassword, userErrorResponseFromApi.getMessage());
        return this;
    }

    // Удаление пользователя
    public UserSteps deleteUser(String token) {
        response = userApi.deleteUser(token).then();
        return this;
    }

    // Проверка успешного удаления
    public UserSteps checkDeleteUser() {
        response.assertThat().statusCode(SC_ACCEPTED);
        return this;
    }

    // Изменение данных пользователя с токеном
    public UserSteps changeUser(UserChangeRequest user, String token) {
        response = userApi.patchUser(user, token).then();
        return this;
    }

    // Изменение данных без токена (для проверки 401)
    public UserSteps changeUser(UserChangeRequest user) {
        response = userApi.patchUser(user).then();
        return this;
    }

    // Проверка успешного изменения данных пользователя
    public UserSteps checkChangeUser(String expectedEmail, String expectedName) {
        response.assertThat().statusCode(SC_OK);
        UserChangeResponse userChangeResponseFromApi = response.extract().body().as(UserChangeResponse.class);
        assertTrue("Ответ сервера должен содержать success = true", userChangeResponseFromApi.isSuccess());
        assertNotNull("Ответ сервера должен содержать объект user", userChangeResponseFromApi.getUser());

        User user = userChangeResponseFromApi.getUser();

        if (expectedEmail != null && expectedName == null) {
            assertEquals("Поле email в объекте user должно совпадать с: " + userData.newEmail, expectedEmail.toLowerCase(), user.getEmail().toLowerCase());
            assertEquals("Поле name в объекте user должно совпадать с: " + userData.name, userData.name, user.getName());
        } else if (expectedName != null && expectedEmail == null) {
            assertEquals("Поле email в объекте user должно совпадать с: " + userData.email, userData.email.toLowerCase(), user.getEmail().toLowerCase());
            assertEquals("Поле name в объекте user должно совпадать с: " + userData.newName, expectedName, user.getName());
        } else if (expectedName != null && expectedEmail != null) {
            assertEquals("Поле email в объекте user должно совпадать с: " + userData.newEmail, expectedEmail.toLowerCase(), user.getEmail().toLowerCase());
            assertEquals("Поле name в объекте user должно совпадать с: " + userData.newName, expectedName, user.getName());
        }

        return this;
    }

    // Проверка неуспешного изменения данных пользователя без авторизации
    public UserSteps checkChangeUserUnauthorized() {
        response.assertThat().statusCode(SC_UNAUTHORIZED);
        UserErrorResponse userErrorResponseFromApi = response.extract().body().as(UserErrorResponse.class);
        assertFalse("Ответ сервера должен содержать success = false", userErrorResponseFromApi.isSuccess());
        assertEquals("Некорректное сообщение об ошибке", messageShouldBeAuthorised, userErrorResponseFromApi.getMessage());
        return this;
    }

    // Проверка неуспешного изменения данных пользователя, если email уже занят
    public UserSteps checkChangeUserEmailExists() {
        response.assertThat().statusCode(SC_FORBIDDEN);
        UserErrorResponse userErrorResponseFromApi = response.extract().body().as(UserErrorResponse.class);
        assertFalse("Ответ сервера должен содержать success = false", userErrorResponseFromApi.isSuccess());
        assertEquals("Некорректное сообщение об ошибке", messageEmailAlreadyExists, userErrorResponseFromApi.getMessage());
        return this;
    }
}
