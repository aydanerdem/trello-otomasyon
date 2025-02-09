package com.trello.main;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import com.trello.utils.Config;

import java.util.List;
import java.util.Map;
import java.util.Random;
import static io.restassured.RestAssured.given;

public class MainTrello {

    private static final String API_KEY = Config.getApiKey();
    private static final String TOKEN = Config.getToken();

    public static List<String> createBoard(String boardName) {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of(
                        "name", boardName,
                        "key", API_KEY,
                        "token", TOKEN
                ))
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return List.of(
                response.path("id"),
                response.path("name")
        );
    }

    public static List<String> createList(String boardId, String listName) {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of(
                        "name", listName,
                        "key", API_KEY,
                        "token", TOKEN
                ))
                .when()
                .post("https://api.trello.com/1/boards/" + boardId + "/lists");


        return List.of(
                response.path("id"),
                response.path("name")
        );
    }

    public static List<String> createCard(String listId, String cardName) {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of(
                        "name", cardName,
                        "idList", listId,
                        "key", API_KEY,
                        "token", TOKEN
                ))
                .when()
                .post("https://api.trello.com/1/cards");
        return List.of(
                response.path("id"),
                response.path("name")
        );
    }

    public static List<String> updateCard(String boardId, String newCardName) {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of(
                        "key", API_KEY,
                        "token", TOKEN
                ))
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/cards");
        List<String> cardIds = (List<String>) response.jsonPath().getList("id", String.class);

        Random rand = new Random();
        String randomCardId = cardIds.get(rand.nextInt(cardIds.size()));

        given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of(

                        "key", API_KEY,
                        "token", TOKEN,
                        "name", newCardName
                ))
                .when()
                .put("https://api.trello.com/1/cards/" + randomCardId)
                .then()
                .statusCode(200);

        return List.of(
                randomCardId,
                response.path("name").toString()
        );
    }

    public static void deleteCard(String cardId) {
        given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of(

                        "key", API_KEY,
                        "token", TOKEN
                ))
                .when()
                .delete("https://api.trello.com/1/cards/" + cardId);
    }

    public static void deleteBoard(String boardId) {
        given()
                .contentType(ContentType.JSON)
                .queryParams(Map.of(

                        "key", API_KEY,
                        "token", TOKEN
                ))
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(200);
    }
}