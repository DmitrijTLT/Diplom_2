package praktikum.order;

import io.restassured.response.ValidatableResponse;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

// Шаги для тестирования API заказов
public class OrderSteps {

    private OrderApi orderApi = new OrderApi();
    private ValidatableResponse response;

    String messageErrorCreateOrder = "Ingredient ids must be provided";
    String messageNotAuth = "You should be authorised";

    // Создание заказа с токеном
    public OrderSteps createOrder(OrderCreateRequest order, String token) {
        response = orderApi.createOrder(order, token).then();
        return this;
    }

    // Создание заказа без токена (для проверки 401)
    public OrderSteps createOrder(OrderCreateRequest order) {
        response = orderApi.createOrder(order).then();
        return this;
    }

    // Проверка успешного создания заказа
    public OrderSteps checkCreateOrder() {
        response.assertThat().statusCode(SC_OK);
        OrderCreateResponse orderCreateResponseFromApi = response.extract().body().as(OrderCreateResponse.class);
        assertNotNull("Ответ сервера должен содержать 'name'", orderCreateResponseFromApi.getName());
        assertNotNull("Ответ сервера должен содержать объект 'order'", orderCreateResponseFromApi.getOrder());
        assertNotNull("Объект 'order' должен содержать 'number'",orderCreateResponseFromApi.getOrder().getNumber());
        assertTrue("Ответ сервера должен содержать success = true", orderCreateResponseFromApi.isSuccess());
        return this;
    }

    // Проверка неуспешного создания заказа с невалидным хеш ингредиента
    public OrderSteps checkNegativeCreateOrderWithInvalidIngredientId() {
       response.assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
       OrderErrorResponse orderErrorResponseFromApi = response.extract().body().as(OrderErrorResponse.class);
       assertFalse("Ответ сервера должен содержать success = false", orderErrorResponseFromApi.isSuccess());
       assertEquals("Некорректное сообщение об ошибке", messageErrorCreateOrder, orderErrorResponseFromApi.getMessage());
       return this;
    }

    // Проверка неуспешного создания заказа с невалидным хеш ингредиента
    public OrderSteps checkNegativeCreateOrderWithoutIngredient() {
        response.assertThat().statusCode(SC_BAD_REQUEST);
        OrderErrorResponse orderErrorResponseFromApi = response.extract().body().as(OrderErrorResponse.class);
        assertFalse("Ответ сервера должен содержать success = false", orderErrorResponseFromApi.isSuccess());
        assertEquals("Некорректное сообщение об ошибке", messageErrorCreateOrder, orderErrorResponseFromApi.getMessage());
        return this;
    }

    // Получение списка ингрединетов
    public OrderSteps getAllIngredients() {
        response = orderApi.getAllIngedients().then();
        return this;
    }

    // Проверка получения списка ингредиентов
    public OrderSteps checkGetAllIngredients() {
        response.assertThat().statusCode(SC_OK);
        IngredientsResponse ingredientsResponseFromApi = response.extract().body().as(IngredientsResponse.class);
        assertTrue("Ответ сервера должен содержать success = true", ingredientsResponseFromApi.isSuccess());
        assertNotNull("Ответ сервера должен содержать массив 'data'", ingredientsResponseFromApi.getData());
        assertFalse("Список ингредиентов не должен быть пустым", ingredientsResponseFromApi.getData().isEmpty());
        // Проверяем каждый ингредиент в списке
        for (Ingredients ingredient : ingredientsResponseFromApi.getData()) {
            assertNotNull("ID ингредиента не должен быть null", ingredient.get_id());
            assertNotNull("Имя ингредиента не должно быть null", ingredient.getName());
            assertNotNull("Тип ингредиента не должен быть null", ingredient.getType());
            assertNotNull("Поле proteins не должно быть null", ingredient.getProteins());
            assertNotNull("Поле fat не должно быть null", ingredient.getFat());
            assertNotNull("Поле carbohydrates не должно быть null", ingredient.getCarbohydrates());
            assertNotNull("Поле calories не должно быть null", ingredient.getCalories());
            assertNotNull("Поле price не должно быть null", ingredient.getPrice());
            assertNotNull("Поле image не должно быть null", ingredient.getImage());
            assertNotNull("Поле image_mobile не должно быть null", ingredient.getImage_mobile());
            assertNotNull("Поле image_large не должно быть null", ingredient.getImage_large());
            assertNotNull("Поле __v не должно быть null", ingredient.get__v());
        }
        return this;
    }

    // Получаем id первой булки в списке ингредиентов
    public String getFirstBunId() {
        IngredientsResponse ingredientsResponseFromApi = response.extract().body().as(IngredientsResponse.class);
        return ingredientsResponseFromApi.getData().stream()
                .filter(type -> "bun".equals(type.getType()))
                .map(Ingredients::get_id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Булка не найдена в списке ингредиентов"));
    }

    // Получаем id первой начинки в списке ингредиентов
    public String getFirstMainId() {
        IngredientsResponse ingredientsResponseFromApi = response.extract().body().as(IngredientsResponse.class);
        return ingredientsResponseFromApi.getData().stream()
                .filter(type -> "main".equals(type.getType()))
                .map(Ingredients::get_id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Начинка не найдена в списке ингредиентов"));
    }

    // Получаем id первого соуса в списке ингредиентов
    public String getFirstSauceId() {
        IngredientsResponse ingredientsResponseFromApi = response.extract().body().as(IngredientsResponse.class);
        return ingredientsResponseFromApi.getData().stream()
                .filter(type -> "sauce".equals(type.getType()))
                .map(Ingredients::get_id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Соус не найден в списке ингредиентов"));
    }

    // Получение заказа пользователя с токеном
    public OrderSteps getOrderForUser(String token) {
        response = orderApi.getOrdersForUser(token).then();
        return this;
    }

    // Получение заказа пользователя без токеном
    public OrderSteps getOrderForUser() {
        response = orderApi.getOrdersForUser().then();
        return this;
    }

    // Проверка неуспешного получения заказов для пользователя без авторизации
    public OrderSteps checkNegativeGetOrderForUserWithoutToken() {
        response.assertThat().statusCode(SC_UNAUTHORIZED);
        OrderErrorResponse orderErrorResponseFromApi = response.extract().body().as(OrderErrorResponse.class);
        assertFalse("Ответ сервера должен содержать success = false", orderErrorResponseFromApi.isSuccess());
        assertEquals("Некорректное сообщение об ошибке", messageNotAuth, orderErrorResponseFromApi.getMessage());
        return this;
    }

    // Проверка успешного получения заказов для пользователя
    public OrderSteps checkGetOrderForUser() {
        response.assertThat().statusCode(SC_OK);
        OrderUserResponse orderUserResponseFromApi = response.extract().body().as(OrderUserResponse.class);
        assertTrue("Ответ сервера должен содержать success = true", orderUserResponseFromApi.isSuccess());
        assertNotNull("Ответ сервера должен содержать массив 'orders'", orderUserResponseFromApi.getOrders());
        assertFalse("Список заказов не должен быть пустым", orderUserResponseFromApi.getOrders().isEmpty());
        assertNotNull("Поле total не должно быть пустым", orderUserResponseFromApi.getTotal());
        assertNotNull("Поле totalToday не должно быть пустым", orderUserResponseFromApi.getTotalToday());
        for (UserOrders orders : orderUserResponseFromApi.getOrders()) {
            assertNotNull("Массив ingredients не должен быть null", orders.getIngredients());
            assertNotNull("Поле id ингредиента не должно быть null", orders.get_id());
            assertNotNull("Поле status заказа не должен быть null", orders.getStatus());
            assertNotNull("Поле number не должно быть null", orders.getNumber());
            assertNotNull("Поле createAt не должно быть null", orders.getCreatedAt());
            assertNotNull("Поле updateAt не должно быть null", orders.getUpdatedAt());
        }
        List<UserOrders> ordersList = orderUserResponseFromApi.getOrders();
        int count = ordersList.size();
        assertTrue(String.format("Превышен лимит заказов: ожидается не более 50, получено: %d", count), count <= 50);
        return this;
    }
}
