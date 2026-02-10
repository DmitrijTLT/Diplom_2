package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.BaseHttpClient;

public class UserApi extends BaseHttpClient {
    private final String postUserRegister = "/api/auth/register";
    private final String postUserLogin = "/api/auth/login";
    private final String getUser = "/api/auth/user";
    private final String patchUser = "/api/auth/user";
    private final String deleteUser = "/api/auth/user";

    @Step("Создание пользователя")
    public Response registerUser(UserRegisterRequest user) {
        return doPostRequest(postUserRegister, user);
    }

    @Step("Авторизация пользователя")
    public Response loginUser(UserLoginRequest login, String token) {
        return doPostRequest(postUserLogin, login, token);
    }

    @Step("Получение данных пользователя")
    public Response getUser(String token) {
        return doGetRequest(getUser, token);
    }

    @Step("Изменение данных пользователя")
    public Response patchUser(UserChangeRequest user, String token) {
        return doPatchRequest(patchUser, user, token);
    }

    @Step("Изменение данных пользователя без авторизации")
    public Response patchUser(UserChangeRequest user) {
        return doPatchRequest(patchUser, user);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String token) {
        return doDeleteRequest(deleteUser, token);
    }
}
