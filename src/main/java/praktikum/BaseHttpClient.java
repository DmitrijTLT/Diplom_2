package praktikum;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseHttpClient {

    public static RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.BASE_URL)
                .addHeader("Content-type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    protected Response doGetRequest(String path) {
        return given()
                .spec(baseRequestSpec())
                .get(path)
                .thenReturn();
    }

    protected Response doGetRequest(String path, String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Токен не может быть null или пустым");
        }
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body, String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Токен не может быть null или пустым");
        }
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doDeleteRequest(String path, String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Токен не может быть null или пустым");
        }
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .delete(path)
                .thenReturn();
    }

    protected Response doPatchRequest(String path, Object body, String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Токен не может быть null или пустым");
        }
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .body(body)
                .patch(path)
                .thenReturn();
    }

    protected Response doPatchRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .patch(path)
                .thenReturn();
    }
}