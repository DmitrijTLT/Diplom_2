package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.aspectj.weaver.patterns.IToken;
import praktikum.BaseHttpClient;

public class OrderApi extends BaseHttpClient {
    private final String postOrderCreate = "/api/orders";
    private final String getIngredients = "/api/ingredients";
    private final String getOrdersAll = "/api/orders/all";
    private final String getUserOrders = "/api/orders";

    @Step("Создание заказа c авторизацией")
    public Response createOrder(OrderCreateRequest order, String token) {
        return doPostRequest(postOrderCreate, order, token);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrder(OrderCreateRequest order) {
        return doPostRequest(postOrderCreate, order);
    }

    @Step("Получение списка игредиентов")
    public Response getAllIngedients() {
        return doGetRequest(getIngredients);
    }

    @Step("Получение списка всех заказов")
    public Response getAllOrders() {
        return doGetRequest(getOrdersAll);
    }

    @Step("Получение заказа пользователя с авторизацией")
    public Response getOrdersForUser(String token) {
        return doGetRequest(getUserOrders, token);
    }

    @Step("Получение заказа пользователя без авторизации")
    public Response getOrdersForUser() {
        return doGetRequest(getUserOrders);
    }
}
